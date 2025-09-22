import React from "react";

function maskNumber(number = ""){
    // we will formated the given number into "#### #### #### 12365"
    const cleaned = number.replace(/\s+/g, "").replace(/[^0-9]/g, "");
    if(!cleaned) return "•••• •••• •••• ••••";
    const parts = [];
    for (let i=0 ; i < cleaned.length ; i += 4){
        parts.push(cleaned.substring(i,i+4));
    }
    // mask all numbers expect for the last one  
    for(let i =0 ; i < parts.length-1 ; i++){
        parts[i] = parts[i].replace(/./g,"•");
    }
    return parts.join(" ");
}

export default function CreditCard({
    name = "CARDHOLDER",
    number = "",
    expiry = "MM/YY",
    brand = "VISA",
    bg = "linear-gradient(135deg , #667eea 0%, #764ba2 100%)",
}){
    const masked = maskNumber(number);
    return (
        <div className="credit-card" style = {{background: bg}}>
            <div className="card-top">
                <div className="chip" aria-hidden = "true">
                {/*simple chip usiing css */}
                </div>
            </div>
            <div className="brand">{brand}</div>

            <div className="card-middle">
                <div className="card-number" aria-label="card number">{masked}</div>
            </div>

            <div className="card-buttom">
                <div className="card-holder">
                    <div className = "label">CARDHOLDER</div>
                    <div className="value">{name.toUpperCase()}</div>
                </div>
                <div className="card-expiry">
                    <div className="label">Expires</div>
                    <div className="Value">{expiry}</div>
                </div>
            </div>

        </div>
    );
}