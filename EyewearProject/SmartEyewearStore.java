<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>بصيرة - النظام المتكامل</title>
    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/@emailjs/browser@3/dist/email.min.js"></script>
    <script> (function() { emailjs.init("Gnwb2INo4rUKM3yDL"); })(); </script>

    <style>
        :root { --bg: #0f172a; --card: #1e293b; --accent: #38bdf8; --text: #f1f5f9; --success: #22c55e; --danger: #ef4444; }
        body { font-family: 'Cairo', sans-serif; background: var(--bg); color: var(--text); margin: 0; padding: 20px; }

        /* رجعنا السلة العايمة */
        .floating-cart {
            position: fixed; left: 20px; top: 20px; background: var(--accent);
            color: var(--bg); padding: 15px; border-radius: 50%; width: 50px; height: 50px;
            display: flex; align-items: center; justify-content: center; font-weight: bold;
            box-shadow: 0 0 15px rgba(56, 189, 248, 0.4); z-index: 1000; animation: pop 0.3s ease;
        }
        @keyframes pop { 0% { transform: scale(1); } 50% { transform: scale(1.2); } 100% { transform: scale(1); } }

        .container { max-width: 800px; margin: auto; }
        header { text-align: center; margin-bottom: 30px; }
        h1 { font-size: 2.5rem; background: linear-gradient(90deg, #38bdf8, #818cf8); -webkit-background-clip: text; -webkit-text-fill-color: transparent; margin:0; }

        .section { background: var(--card); padding: 20px; border-radius: 15px; margin-bottom: 20px; border: 1px solid rgba(255,255,255,0.05); }
        .btn-group { display: flex; gap: 10px; justify-content: center; flex-wrap: wrap; }
        
        button { padding: 12px 20px; border: none; border-radius: 12px; cursor: pointer; font-family: 'Cairo'; font-weight: bold; transition: 0.3s; }
        .btn-opt { background: rgba(255,255,255,0.1); color: white; flex: 1; }
        .btn-opt:hover { background: var(--accent); color: var(--bg); }

        .preview-area { display: flex; flex-wrap: wrap; gap: 10px; justify-content: center; margin-top: 15px; }
        .preview-img { width: 60px; height: 45px; border-radius: 8px; border: 1px solid var(--accent); background: white; object-fit: contain; }

        .modal { display: none; position: fixed; z-index: 2000; left: 0; top: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.9); backdrop-filter: blur(8px); }
        .modal-content { background: var(--card); margin: 5% auto; padding: 25px; width: 90%; max-width: 500px; border-radius: 20px; border: 1px solid var(--accent); text-align: center; }

        .item-row { display: flex; align-items: center; gap: 10px; background: rgba(0,0,0,0.2); padding: 10px; border-radius: 10px; margin-bottom: 10px; }
        .item-row img { width: 50px; border-radius: 5px; }

        .final-btn { background: var(--success); color: white; width: 100%; font-size: 1.2rem; padding: 15px; margin-top: 15px; }
        
        /* رسائل الحالة */
        #status-msg { margin-top: 15px; font-weight: bold; padding: 10px; border-radius: 8px; display: none; }
        .msg-success { background: rgba(34, 197, 94, 0.2); color: var(--success); }
        .msg-error { background: rgba(239, 68, 68, 0.2); color: var(--danger); }
    </style>
</head>
<body>

<div class="floating-cart" id="cart-counter">🛒 0</div>

<div class="container">
    <header>
        <h1>بصيرة</h1>
        <p>إدارة الطلبات الذكية</p>
    </header>

    <div class="section">
        <h3>👓 النظارات الطبية</h3>
        <div class="btn-group">
            <button class="btn-opt" onclick="openProducts('بلاستيك طبية')">بلاستيك</button>
            <button class="btn-opt" onclick="openProducts('تيتانيوم طبية')">تيتانيوم</button>
            <button class="btn-opt" onclick="openProducts('معدن طبية')">معدن</button>
        </div>
        <div class="preview-area" id="med-preview"></div>
    </div>

    <div class="section">
        <h3>🕶️ النظارات الشمسية</h3>
        <div class="btn-group">
            <button class="btn-opt" onclick="openProducts('شمس رجالي')">رجالي</button>
            <button class="btn-opt" onclick="openProducts('شمس حريمي')">حريمي</button>
        </div>
        <div class="preview-area" id="sun-preview"></div>
    </div>

    <button onclick="startCheckout()" style="background: var(--accent); color: var(--bg); width: 100%; font-size: 1.3rem; margin-top: 10px;">مراجعة الطلب والدفع</button>
</div>

<div id="productModal" class="modal">
    <div class="modal-content">
        <h2 id="modalTitle" style="color: var(--accent); margin-top:0;"></h2>
        <div id="gallery"></div>
        <button onclick="closeModal('productModal')" style="background: var(--accent); color: var(--bg); width: 100%; margin-top:15px;">تم</button>
    </div>
</div>

<div id="reviewModal" class="modal">
    <div class="modal-content">
        <h2 style="color: var(--success);">فاتورة بصيرة</h2>
        
        <select id="payMethod" style="width: 100%; padding: 12px; border-radius: 10px; background: #0f172a; color: white; border: 1px solid var(--accent); margin-bottom: 15px; font-family: 'Cairo';">
            <option value="عند الاستلام">الدفع عند الاستلام</option>
            <option value="فودافون كاش">فودافون كاش</option>
            <option value="انستا باي">انستا باي</option>
        </select>

        <div id="summaryList" style="max-height: 200px; overflow-y: auto; text-align: right; margin-bottom: 15px;"></div>

        <div id="status-msg"></div>

        <button id="confirmBtn" class="final-btn" onclick="sendToEmailJS()">تأكيد وإرسال الطلب 🚀</button>
        <p id="backBtn" onclick="closeModal('reviewModal')" style="cursor:pointer; color: #94a3b8; margin-top: 10px;">رجوع للتعديل</p>
    </div>
</div>

<script>
    let cart = [];

    // صور افتراضية (تقدر تغيرها لصورك الحقيقية في images/)
    const myImages = {
        'بلاستيك طبية': ['p1.jpg', 'p2.jpg', 'p3.jpg'],
        'تيتانيوم طبية': ['t1.jpg', 't2.jpg', 't3.jpg'],
        'معدن طبية': ['m1.jpg', 'm2.jpg', 'm3.jpg'],
        'شمس رجالي': ['sr1.jpg', 'sr2.jpg', 'sr3.jpg'],
        'شمس حريمي': ['sw1.jpg', 'sw2.jpg', 'sw3.jpg']
    };

    function openProducts(cat) {
        document.getElementById('modalTitle').innerText = cat;
        const gallery = document.getElementById('gallery');
        gallery.innerHTML = "";
        const imgs = myImages[cat] || ['1.jpg', '2.jpg', '3.jpg'];

        for(let i=0; i<3; i++) {
            let name = `${cat} - م${i+1}`;
            let imgPath = `images/${imgs[i]}`;
            let count = cart.filter(x => x.name === name).length;
            gallery.innerHTML += `
                <div class="item-row">
                    <img src="${imgPath}" onerror="this.src='https://via.placeholder.com/50?text=IMG'">
                    <span style="flex:1">${name}</span>
                    <button onclick="updateCart('${name}', '${imgPath}', 1)" style="background:var(--success); border-radius:50%; width:30px; height:30px;">+</button>
                    <span id="qty-${name}">${count}</span>
                    <button onclick="updateCart('${name}', '${imgPath}', -1)" style="background:var(--danger); border-radius:50%; width:30px; height:30px;">-</button>
                </div>`;
        }
        document.getElementById('productModal').style.display = 'block';
    }

    function updateCart(name, img, delta) {
        if (delta === 1) {
            cart.push({name, img});
        } else {
            const idx = cart.findIndex(x => x.name === name);
            if (idx > -1) cart.splice(idx, 1);
        }
        if(document.getElementById(`qty-${name}`)) document.getElementById(`qty-${name}`).innerText = cart.filter(x => x.name === name).length;
        
        // تحديث السلة العايمة
        document.getElementById('cart-counter').innerText = "🛒 " + cart.length;
        document.getElementById('cart-counter').style.animation = "none";
        setTimeout(() => document.getElementById('cart-counter').style.animation = "pop 0.3s ease", 10);
        
        renderPreviews();
    }

    function renderPreviews() {
        const med = document.getElementById('med-preview');
        const sun = document.getElementById('sun-preview');
        med.innerHTML = ""; sun.innerHTML = "";
        cart.forEach(item => {
            let imgTag = document.createElement('img');
            imgTag.src = item.img;
            imgTag.className = "preview-img";
            imgTag.onerror = function() { this.src = 'https://via.placeholder.com/50?text=IMG'; };
            if(item.name.includes('شمس')) sun.appendChild(imgTag);
            else med.appendChild(imgTag);
        });
    }

    function startCheckout() {
        if(cart.length === 0) return alert("السلة فاضية!");
        const list = document.getElementById('summaryList');
        list.innerHTML = "";
        const unique = [...new Set(cart.map(i => i.name))];
        unique.forEach(uName => {
            const count = cart.filter(x => x.name === uName).length;
            const item = cart.find(x => x.name === uName);
            list.innerHTML += `<div class="item-row"><img src="${item.img}" onerror="this.src='https://via.placeholder.com/50?text=IMG'"><span>${uName} (العدد: ${count})</span></div>`;
        });
        
        // ريسيت لرسائل الحالة عند فتح الفاتورة
        document.getElementById('status-msg').style.display = 'none';
        document.getElementById('confirmBtn').style.display = 'block';
        document.getElementById('reviewModal').style.display = 'block';
    }

    function sendToEmailJS() {
        const btn = document.getElementById('confirmBtn');
        const status = document.getElementById('status-msg');
        const pay = document.getElementById('payMethod').value;
        
        btn.style.display = 'none';
        status.innerText = "جاري الإرسال... ⏳";
        status.className = "msg-success";
        status.style.display = 'block';

        const productNames = cart.map(i => i.name).join(", ");

        emailjs.send("service_4alx2zj", "template_zek3cc2", {
            customer_name: "عميل بصيرة",
            order_details: productNames,
            payment_method: pay
        })
        .then(() => {
            status.innerText = "تمت العملية بنجاح! ✅ جاري تحديث الصفحة...";
            status.className = "msg-success";
            setTimeout(() => location.reload(), 3000); // ريسيت كامل بعد 3 ثواني
        })
        .catch((err) => {
            status.innerText = "حدث خطأ أثناء الإرسال! ❌ حاول مرة أخرى.";
            status.className = "msg-error";
            btn.style.display = 'block';
            console.error("EmailJS Error:", err);
        });
    }

    function closeModal(id) { document.getElementById(id).style.display = 'none'; }
</script>
</body>
</html>