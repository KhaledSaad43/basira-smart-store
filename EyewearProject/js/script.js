// 1. Singleton Cart Manager + LocalStorage
const ShoppingCartManager = (function() {
    let instance;
    function createInstance() {
        let cart = JSON.parse(localStorage.getItem("cart")) || [];

        function saveCart() {
            localStorage.setItem("cart", JSON.stringify(cart));
        }

        return {
            addItem: (item) => {
                cart.push(item);
                saveCart();
            },
            removeItem: (name) => {
                const idx = cart.findLastIndex(x => x.name === name);
                if (idx > -1) cart.splice(idx, 1);
                saveCart();
            },
            getCart: () => cart,
            clear: () => {
                cart = [];
                localStorage.removeItem("cart");
            }
        };
    }
    return {
        getInstance: () => {
            if (!instance) instance = createInstance();
            return instance;
        }
    };
})();

const myCart = ShoppingCartManager.getInstance();

// EmailJS
(function() { emailjs.init("Gnwb2INo4rUKM3yDL"); })();


// 🏭 Factory Pattern
function ProductFactory(name, category, price, img) {
    return {
        name,
        category,
        price,
        img
    };
}


// 🏗️ Builder Pattern
class OrderBuilder {
    constructor() {
        this.order = {};
    }

    setUser(name, phone) {
        this.order.customer_name = name;
        this.order.customer_phone = phone;
        return this;
    }

    setItems(cart) {
        this.order.orders = [...new Set(cart.map(i => i.name))].map(uName => ({
            name: uName,
            units: cart.filter(x => x.name === uName).length,
            price: cart.find(x => x.name === uName).price
        }));
        return this;
    }

    setPayment(method) {
        this.order.payment_method = method;
        return this;
    }

    setTotal(cart) {
        this.order.total_price = cart.reduce((sum, item) => sum + item.price, 0);
        return this;
    }

    setOrderId() {
        this.order.order_id = Math.floor(Math.random() * 100000);
        return this;
    }

    build() {
        return this.order;
    }
}


// الأسعار
const categoryPrices = {
    'بلاستيك طبية': 550,
    'تيتانيوم طبية': 1250,
    'معدن طبية': 850,
    'شمس رجالي': 700,
    'شمس حريمي': 750
};

// الصور
const myImages = {
    'بلاستيك طبية': ['pl1.jpg', 'pl2.jpg', 'pl3.jpg'],
    'تيتانيوم طبية': ['t1.webp', 't2.webp', 'p1.jpg'],
    'معدن طبية': ['m1.jpg', 'm2.png', 'm3.jpg'],
    'شمس رجالي': ['sr1.jpg', 'sr2.webp', 'sr3.webp'],
    'شمس حريمي': ['sw1.webp', 'sw2.webp', 'sw3.png']
};

let selectedPaymentMethod = "";
let prescriptionStatus = "لم يتم الرفع";


// رفع الروشتة
function handlePrescription(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            prescriptionStatus = "✅ مرفق صورة المقاسات";
            document.getElementById('upload-inner').style.display = 'none';
            document.getElementById('prescription-preview-container').innerHTML =
                `<img src="${e.target.result}" style="width:100%; border-radius:10px;">`;
        };
        reader.readAsDataURL(file);
    }
}


// فتح المنتجات
function openProducts(cat) {
    document.getElementById('modalTitle').innerText = cat;
    const gallery = document.getElementById('gallery');
    gallery.innerHTML = "";
    const price = categoryPrices[cat];

    (myImages[cat] || []).forEach((img, i) => {
        const name = `${cat} - م${i+1}`;
        const count = myCart.getCart().filter(x => x.name === name).length;

        gallery.innerHTML += `
        <div class="item-row">
            <img src="images/${img}" onerror="this.src='https://via.placeholder.com/50'" class="preview-img">
            <div style="flex:1; text-align:right; margin-right:10px">
                <div style="font-weight:bold; font-size:0.9rem">${name}</div>
                <div style="color:var(--accent); font-size:0.8rem">${price} ج.م</div>
            </div>
            <div style="display:flex; align-items:center; gap:12px">
                <button onclick="updateCartUI('${name}', '${cat}', 'images/${img}', -1, ${price})" class="qty-btn btn-minus">-</button>
                <span id="qty-${name}">${count}</span>
                <button onclick="updateCartUI('${name}', '${cat}', 'images/${img}', 1, ${price})" class="qty-btn btn-plus">+</button>
            </div>
        </div>`;
    });

    document.getElementById('productModal').style.display = 'block';
}


// تحديث السلة
function updateCartUI(name, category, img, delta, price) {
    if (delta === 1) {
        const product = ProductFactory(name, category, price, img);
        myCart.addItem(product);

        const btn = event.target;
        const parentImg = btn.closest('.item-row').querySelector('img');
        animateToCart(img, parentImg);

        playFeedback();
    } else {
        myCart.removeItem(name);
    }

    const countLabel = document.getElementById(`qty-${name}`);
    if (countLabel) countLabel.innerText =
        myCart.getCart().filter(x => x.name === name).length;

    updateCartDisplay();
    renderPreviews();
}


// تحديث عرض السلة
function updateCartDisplay() {
    const cart = myCart.getCart();
    const total = cart.reduce((sum, item) => sum + item.price, 0);

    document.querySelector('#cart-counter .count').innerText = `🛒 ${cart.length}`;
    document.querySelector('#cart-counter .total').innerText = `${total} ج.م`;
}


// عرض الصور
function renderPreviews() {
    const med = document.getElementById('med-preview');
    const sun = document.getElementById('sun-preview');
    med.innerHTML = "";
    sun.innerHTML = "";

    myCart.getCart().forEach(item => {
        let img = document.createElement('img');
        img.src = item.img;
        img.className = "preview-img";

        if(item.name.includes('شمس')) sun.appendChild(img);
        else med.appendChild(img);
    });
}


// الدفع
function startPaymentFlow() {
    if(myCart.getCart().length === 0) return alert("السلة فاضية!");
    document.getElementById('paymentModal').style.display = 'block';
}

function selectPayment(method) {
    selectedPaymentMethod = method;
    document.getElementById('paymentModal').style.display = 'none';
    showReviewModal();
}


// صفحة المراجعة
function showReviewModal() {
    const list = document.getElementById('summaryList');
    let totalOrder = 0;

    list.innerHTML = `
        <p style="text-align:right; font-size:0.8rem">
        💳 الدفع: ${selectedPaymentMethod}<br>
        📋 الروشتة: ${prescriptionStatus}
        </p><hr style="opacity:0.1">
    `;

    const uniqueNames = [...new Set(myCart.getCart().map(i => i.name))];

    uniqueNames.forEach(uName => {
        const items = myCart.getCart().filter(x => x.name === uName);
        const count = items.length;
        const subTotal = items[0].price * count;
        totalOrder += subTotal;

        list.innerHTML += `
        <div style="display:flex; justify-content:space-between">
            <span>${uName} (x${count})</span>
            <span>${subTotal} ج.م</span>
        </div>`;
    });

    list.innerHTML += `
        <hr>
        <div style="display:flex; justify-content:space-between; font-weight:bold; color:var(--accent)">
            <span>الإجمالي:</span>
            <span>${totalOrder} ج.م</span>
        </div>
    `;

    document.getElementById('reviewModal').style.display = 'block';
}


// إرسال الطلب (Builder هنا 🔥)
function sendToEmailJS() {
    const name = document.getElementById('user-name').value;
    const phone = document.getElementById('user-phone').value;

    const phoneRegex = /^01[0-2,5]{1}[0-9]{8}$/;

    if (!name || !phone) return alert("ادخل البيانات كاملة");
    if (!phoneRegex.test(phone)) return alert("رقم غير صحيح");

    document.getElementById('confirmBtn').style.display = 'none';
    const status = document.getElementById('status-msg');
    status.style.display = 'block';
    status.innerText = "جاري الإرسال... ⏳";

    const order = new OrderBuilder()
        .setUser(name, phone)
        .setItems(myCart.getCart())
        .setPayment(selectedPaymentMethod)
        .setTotal(myCart.getCart())
        .setOrderId()
        .build();

    emailjs.send("service_4alx2zj", "template_zek3cc2", order)
    .then(() => {
        status.innerText = "تم الطلب بنجاح ✅";
        myCart.clear();
        setTimeout(() => location.reload(), 2500);
    });
}


// غلق المودال
function closeModal(id) {
    document.getElementById(id).style.display = 'none';
}


// أنيميشن الطيران
function animateToCart(imgSrc, startElement) {
    const cart = document.getElementById('cart-counter');
    const rectStart = startElement.getBoundingClientRect();
    const rectEnd = cart.getBoundingClientRect();

    const img = document.createElement('img');
    img.src = imgSrc;
    img.className = "fly-img";

    img.style.top = rectStart.top + "px";
    img.style.left = rectStart.left + "px";

    document.body.appendChild(img);

    setTimeout(() => {
        img.style.top = rectEnd.top + "px";
        img.style.left = rectEnd.left + "px";
        img.style.transform = "scale(0.3)";
        img.style.opacity = "0.5";
    }, 10);

    setTimeout(() => img.remove(), 800);
}


// صوت + فيبريشن
function playFeedback() {
    const sound = document.getElementById("addSound");
    if (sound) sound.play().catch(()=>{});

    if (navigator.vibrate) {
        navigator.vibrate(50);
    }
}


// عند تحميل الصفحة
window.onload = () => {
    updateCartDisplay();
    renderPreviews();
};