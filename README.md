# 🕶️ Basira Smart Glasses Store

![GitHub repo size](https://img.shields.io/github/repo-size/USERNAME/basira-smart-store)
![GitHub last commit](https://img.shields.io/github/last-commit/USERNAME/basira-smart-store)
![HTML](https://img.shields.io/badge/HTML-5-orange)
![CSS](https://img.shields.io/badge/CSS-3-blue)
![JavaScript](https://img.shields.io/badge/JavaScript-ES6-yellow)
![Status](https://img.shields.io/badge/status-active-success)

---

## 🚀 Live Demo
👉 [https://KhaledSaad43.github.io/basira-smart-store/](https://basira-optical-store.netlify.app/)

---

## 📸 Preview

### 🛍️ Home Page
![Home](images/home.png)

### 🛒 Cart Animation
![Cart](images/cart.png)

### 💳 Checkout Flow
![Checkout](images/checkout.png)
---

# 🧠 About the Project

**Basira** is a smart eyewear e-commerce front-end project that simulates a real online store experience with:

- Smart shopping cart system
- Animated UI interactions
- Order checkout flow
- EmailJS order submission
- Mobile responsive design

---

# ✨ Features

## 🛍️ Product System
- Medical glasses + sunglasses categories
- Dynamic product rendering
- Real images per category
- Price system per product type

## 🛒 Smart Cart System
- Add / remove products dynamically
- Live cart counter + total price
- Persistent storage using LocalStorage
- Quantity tracking per item

## 🎯 UI/UX Experience
- Smooth animations (hover, click, transitions)
- Floating WhatsApp button
- Animated background bubbles
- Glassmorphism modern design
- Mobile responsive layout

## 📤 Order System
- Upload prescription image
- Multi-step checkout flow
- Payment method selection
- Order review page
- EmailJS integration (real order sending)

## ⚡ Extra Interactions
- Flying animation when adding product ✈️
- Sound + vibration feedback 🔊📳
- Live preview section
- Floating cart with live updates

---

# 🧠 Design Patterns Used

## 1. Singleton Pattern 🧩

### 📌 Where used:
`ShoppingCartManager`

### 📌 Why used:
To ensure that **only one cart instance exists** across the whole application.

### 📌 Explanation:
Instead of creating multiple carts, we use one global controlled instance that:
- Stores all cart items
- Handles add/remove logic
- Syncs with LocalStorage

### 📌 Benefit:
✔ Prevents data inconsistency  
✔ Centralized cart control  
✔ Easier state management  

---

## 2. Modular Design (Separation of Concerns) 🧱

### 📌 Structure:
- HTML → Structure
- CSS → Styling + animations
- JS → Logic + interactions

### 📌 Why used:
To make the project:
- Easy to maintain
- Easy to scale
- Easy to debug

---

## 3. MVC-Like Thinking (Light Version) 🧠

Not full MVC framework, but conceptually:

### Model:
- Cart data (LocalStorage + JS object)

### View:
- HTML + DOM rendering

### Controller:
- JS functions (add/remove/update cart)

---

## 4. Event-Driven Architecture ⚡

### 📌 Idea:
Everything happens based on user actions:
- Click add to cart
- Open modal
- Select payment
- Submit order

### 📌 Benefit:
✔ Smooth UX  
✔ Real-time updates  
✔ Clean interaction flow  

---

# 🛠️ Tech Stack

- HTML5
- CSS3 (Flexbox + Animations + Glass UI)
- JavaScript ES6+
- EmailJS API

---

# 📂 Project Structure


basira-smart-store/
│
├── index.html
├── css/style.css
├── js/script.js
├── images/
│ ├── products
│ ├── gifs (demo)
│
└── README.md


---

# 🎯 Key System Flow


User selects product
↓
Add to cart (Singleton updates state)
↓
UI updates (counter + preview)
↓
Checkout flow starts
↓
Payment selection
↓
Order review
↓
EmailJS sends order


---

# 📱 Responsive Design

✔ Mobile optimized  
✔ Tablet friendly  
✔ Desktop full experience  

---

# 👨‍💻 Developer

**Khaled Saad**

- GitHub: [https://github.com/USERNAME](https://github.com/KhaledSaad43)
- LinkedIn: [YOUR LINK](https://www.linkedin.com/in/khaled-saad-b78ba8307?utm_source=share_via&utm_content=profile&utm_medium=member_android)
- Facebook: [YOUR LINK](https://www.facebook.com/share/1EYJLXk5g6/)

---

# ⚠️ Notes

- This is a frontend-only project
- No backend database
- Orders sent via EmailJS service

---

# ⭐ If you like it

Give it a ⭐ on GitHub
