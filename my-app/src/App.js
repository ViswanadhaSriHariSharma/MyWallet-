import logo from './logo.svg';
import './App.css';
import CreditCard from './CreditCard';
import Footer from './Footer';
import Navbar from './Navbar';
import React from 'react';
function App() {
  return (
   <div className='app'>
    <Navbar />

    <main className='main'>
      <h2 className='section-title'>MY Cards</h2>
      <div className='cards-grid'>
      <CreditCard
        name = "Sri"
        number = "458965471239"
        expiry = "12/27"
        brand = "VISA"
        bg = "linear-gradient(135deg,#667eea 0%,#764ba2 100%)"
        />
        <CreditCard
        name = "Hari"
        number = "852665479878"
        expiry = "12/29"
        brand = "MASTERCARD"
        bg = "linear-gradient(135deg,#667eea 0%,#764ba2 100%)"
        />
        <CreditCard
        name = "Viswanadha"
        number = ""
        expiry = "--/--"
        brand = "AMEX"
        bg = "linear-gradient(135deg,#11998e 0%,#38ef7d 100%)"
        />
        </div>
    </main>
    <Footer/>
   </div>
  );
}

export default App;
