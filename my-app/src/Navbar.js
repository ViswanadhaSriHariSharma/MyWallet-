import React from "react";

export default function Navbar(){
    return (
        <header className="navbar">
            <div className="nav-left">
                <h1 className="brand">MyWallet</h1>
            </div>
            <nav className="nav-right">
                <ul>
                    <li>Home</li>
                    <li>Cards</li>
                    <li>Settings</li>
                </ul>
            </nav>
        </header>
    );
}