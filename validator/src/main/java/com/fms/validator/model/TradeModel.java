package com.fms.validator.model;

public class TradeModel
{
    private String legalEntity;

    private String premium;

    private String direction;
    
    private String valueDate;

    private String expiryDate;

    private String strategy;

    private String excerciseStartDate;

    private String customer;

    private String type;

    private String payCcy;

    private String amount2;

    private double amount1;

    private String tradeDate;

    private double rate;

    private String style;

    private String ccyPair;

    private String premiumCcy;

    private String premiumType;

    private String deliveryDate;

    private String trader;

    private String premiumDate;

    public String getLegalEntity ()
    {
        return legalEntity;
    }

    public void setLegalEntity (String legalEntity)
    {
        this.legalEntity = legalEntity;
    }

    public String getPremium ()
    {
        return premium;
    }

    public void setPremium (String premium)
    {
        this.premium = premium;
    }

    public String getDirection ()
    {
        return direction;
    }

    public void setDirection (String direction)
    {
        this.direction = direction;
    }

    public String getExpiryDate ()
    {
        return expiryDate;
    }

    public void setExpiryDate (String expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public String getStrategy ()
    {
        return strategy;
    }

    public void setStrategy (String strategy)
    {
        this.strategy = strategy;
    }

    public String getExcerciseStartDate ()
    {
        return excerciseStartDate;
    }

    public void setExcerciseStartDate (String excerciseStartDate)
    {
        this.excerciseStartDate = excerciseStartDate;
    }

    public String getCustomer ()
    {
        return customer;
    }

    public void setCustomer (String customer)
    {
        this.customer = customer;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getPayCcy ()
    {
        return payCcy;
    }

    public void setPayCcy (String payCcy)
    {
        this.payCcy = payCcy;
    }

    public String getAmount2 ()
    {
        return amount2;
    }

    public void setAmount2 (String amount2)
    {
        this.amount2 = amount2;
    }

    public double getAmount1 ()
    {
        return amount1;
    }

    public void setAmount1 (double amount1)
    {
        this.amount1 = amount1;
    }

    public String getTradeDate ()
    {
        return tradeDate;
    }

    public void setTradeDate (String tradeDate)
    {
        this.tradeDate = tradeDate;
    }

    public double getRate ()
    {
        return rate;
    }

    public void setRate (double rate)
    {
        this.rate = rate;
    }

    public String getStyle ()
    {
        return style;
    }

    public void setStyle (String style)
    {
        this.style = style;
    }

    public String getCcyPair ()
    {
        return ccyPair;
    }

    public void setCcyPair (String ccyPair)
    {
        this.ccyPair = ccyPair;
    }

    public String getPremiumCcy ()
    {
        return premiumCcy;
    }

    public void setPremiumCcy (String premiumCcy)
    {
        this.premiumCcy = premiumCcy;
    }

    public String getPremiumType ()
    {
        return premiumType;
    }

    public void setPremiumType (String premiumType)
    {
        this.premiumType = premiumType;
    }

    public String getDeliveryDate ()
    {
        return deliveryDate;
    }

    public void setDeliveryDate (String deliveryDate)
    {
        this.deliveryDate = deliveryDate;
    }

    public String getTrader ()
    {
        return trader;
    }

    public void setTrader (String trader)
    {
        this.trader = trader;
    }

    public String getPremiumDate ()
    {
        return premiumDate;
    }

    public void setPremiumDate (String premiumDate)
    {
        this.premiumDate = premiumDate;
    }
    
    

    public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	@Override
    public String toString()
    {
        return "TradeModel [legalEntity = "+legalEntity+", premium = "+premium+", direction = "+direction+", valueDate = "+valueDate+", expiryDate = "+expiryDate+", strategy = "+strategy+", excerciseStartDate = "+excerciseStartDate+", customer = "+customer+", type = "+type+", payCcy = "+payCcy+", amount2 = "+amount2+", amount1 = "+amount1+", tradeDate = "+tradeDate+", rate = "+rate+", style = "+style+", ccyPair = "+ccyPair+", premiumCcy = "+premiumCcy+", premiumType = "+premiumType+", deliveryDate = "+deliveryDate+", trader = "+trader+", premiumDate = "+premiumDate+"]";
    }
}

