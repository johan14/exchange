package com.mobilelife.exchange.model;

public enum Currency {
  EUR("EUR"), USD("USD"), JPY("JPY"), BGN("BGN"), CZK("CZK"), DKK("DKK"), GBP("GBP"), HUF(
      "HUF"), PLN("PLN"), RON("RON"), SEK("SEK"), CHF("CHF"), ISK("ISK"), NOK("NOK"), HRK(
      "HRK"), RUB("RUB"), TRY("TRY"), AUD("AUD"), BRL("BRL"), CAD("CAD"), CNY("CNY"), HKD(
      "HKD"), IDR("IDR"), ILS("ILS"), INR("INR"), KRW("KRW"), MXN("MXN"), MYR("MYR"), NZD(
      "NZD"), PHP("PHP"), SGD("SGD"), THB("THB"), ZAR("ZAR"), AED("AED"), AFN("AFN"), ALL(
      "ALL"), AMD("AMD"), ANG("ANG"), AOA("AOA"), ARS("ARS"), AWG("AWG"), AZN(
      "AZN"), BAM("BAM"), BBD("BBD"), BDT("BDT"), BHD("BHD"), BIF("BIF"), BMD("BMD"), BND(
      "BND"), BOB("BOB"), BSD("BSD"), BTC("BTC"), BTN("BTN"), BWP("BWP"), BYN(
      "BYN"), BYR("BYR"), BZD("BZD"), CDF("CDF"), CLF("CLF"), CLP(
      "CLP"), COP("COP"), CRC("CRC"), CUC("CUC"), CUP("CUP"), CVE("CVE"), DJF("DJF"), DOP(
      "DOP"), DZD("DZD"), EGP("EGP"), ERN("ERN"), ETB(
      "ETB"), FJD("FJD"), FKP("FKP"), GEL("GEL"), GGP("GGP"), GHS(
      "GHS"), GIP("GIP"), GMD("GMD"), GNF("GNF"), GTQ("GTQ"), GYD("GYD"), HNL(
      "HNL"), HTG("HTG"), IMP("IMP"), IQD("IQD"), IRR("IRR"), JEP("JEP"), JMD("JMD"), JOD(
      "JOD"), KES("KES"), KGS("KGS"), KHR("KHR"), KMF("KMF"), KPW("KPW"), KWD(
      "KWD"), KYD("KYD"), KZT("KZT"), LAK("LAK"), LBP("LBP"), LKR("LKR"), LRD("LRD"), LSL(
      "LSL"), LTL("LTL"), LVL("LVL"), LYD("LYD"), MAD("MAD"), MDL("MDL"), MGA("MGA"), MKD(
      "MKD"), MMK("MMK"), MNT("MNT"), MOP("MOP"), MRO("MRO"), MUR("MUR"), MVR("MVR"), MWK(
      "MWK"), MZN("MZN"), NAD("NAD"), NGN("NGN"), NIO("NIO"), NPR("NPR"), OMR("OMR"), PAB(
      "PAB"), PEN("PEN"), PGK("PGK"), PKR("PKR"), PYG("PYG"), QAR("QAR"), RSD("RSD"), RWF(
      "RWF"), SAR("SAR"), SBD("SBD"), SCR("SCR"), SDG("SDG"), SHP("SHP"), SLL("SLL"), SOS(
      "SOS"), SRD("SRD"), STD("STD"), SVC("SVC"), SYP(
      "SYP"), SZL("SZL"), TJS("TJS"), TMT("TMT"), TND("TND"), TOP("TOP"), TTD("TTD"), TWD(
      "TWD"), TZS("TZS"), UAH("UAH"), UGX("UGX"), UYU(
      "UYU"), UZS("UZS"), VEF("VEF"), VND("VND"), VUV("VUV"), WST("WST"), XAF("XAF"), XAG(
      "XAG"), XAU("XAU"), XCD("XCD"), XDR("XDR"), XOF("XOF"), XPF("XPF"), YER("YER"), ZMK(
      "ZMK"), ZMW("ZMW"), ZWL("ZWL");

  private final String value;

  Currency(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
