package com.example.mini_projet;
public class ConversionUtils {

    public static double convertMoneyDT(double dt, String currency) {
        switch (currency) {
            case "Euro":
                return dt * 0.30;
            case "Dollar":
                return dt * 0.33;
            case "TND -> GBP":
                return dt * 0.25;
            case "Yen":
                return dt * 44.0;
            default:
                return dt;
        }
    }

    public static double convertDTtoCrypto(double dt, String crypto) {
        switch (crypto) {
            case "BTC":
                return dt * 0.000005;
            case "ETH":
                return dt * 0.00008;
            case "XRP":
                return dt * 1.95;
            case "ADA":
                return dt * 0.9;
            default:
                return 0.0;
        }
    }


    public static double convertDTtoMetal(double dt, String metal) {
        switch (metal) {
            case "Or (24K)":
                return dt * 0.0017; // gramme
            case "Or (18K)":
                return dt * 0.0013;
            case "Argent":
                return dt * 0.13;
            case "Platine":
                return dt * 0.0009;
            default:
                return 0.0;
        }
    }
}
