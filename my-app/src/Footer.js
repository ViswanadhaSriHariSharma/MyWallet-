// contains the footer function
import React from "react";

export default function Footer(){
    return (
        <footer className="footer">
            <p>© {new Date().getFullYear} MyWallet.All rights reserved</p>
        </footer>
    );
}