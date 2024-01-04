package dev.gabul.pagseguro_smart_flutter.core;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagTransactionResult;
import com.google.gson.Gson;

public class ActionResult {

  String transactionCode;

  String transactionId;

  String message;

  int eventCode;

  String errorCode;

  int result = 0;

  int isBuildResponse = 0;

  public int getBuildResponse() {
    if (isBuildResponse != 0) {
      int response = this.isBuildResponse;
      this.isBuildResponse = 0;
      return response;
    }
    return 0;
  }

  public String getTransactionCode() {
    return transactionCode;
  }

  public void setTransactionCode(String transactionCode) {
    this.transactionCode = transactionCode;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getEventCode() {
    return eventCode;
  }

  public void setEventCode(int eventCode) {
    this.eventCode = eventCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }

  private String mode = "RESPONSE_PAGSEGURO";
  private String cardBin;
  private String cardBrand;
  private int resultCode;
  private String amount;
  private String hostNSU;
  private String nsu;
  private String transactionID;
  private String date;
  private String time;
  private String terminalSerialNumber;
  private String errorMessage;
  private int paymentType;

  public int getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(int type) {
    this.paymentType = type;
  }

  public String getCardBin() {
    return cardBin;
  }

  public void setCardBin(String bin) {
    this.cardBin = bin;
  }

  public String getCardBrand() {
    return cardBrand;
  }

  public void setCardBrand(String brand) {
    this.cardBrand = brand;
  }

  public int getResultCode() {
    return resultCode;
  }

  public void setResultCode(int result) {
    this.resultCode = result;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String value) {
    this.amount = value;
  }

  public String getHostNSU() {
    return hostNSU;
  }

  public void setHostNSU(String host) {
    this.hostNSU = host;
  }

  public String getNsu() {
    return nsu;
  }

  public void setNsu(String nsuValue) {
    this.nsu = nsuValue;
  }

  public String getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(String id) {
    this.transactionID = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String dateResult) {
    this.date = dateResult;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String timeResult) {
    this.time = timeResult;
  }

  public String getTerminalSerialNumber() {
    return terminalSerialNumber;
  }

  public void setTerminalSerialNumber(String serial) {
    this.terminalSerialNumber = serial;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMsg) {
    this.errorMessage = errorMsg;
  }

  public void buildResponse(PlugPagTransactionResult result) {
    setCardBin(result.getBin());
    setCardBrand(result.getCardBrand());
    setResultCode(result.getResult());
    setErrorCode(result.getErrorCode());
    setAmount(result.getAmount());
    setHostNSU(result.getHostNsu());
    setNsu(result.getNsu());
    setTransactionID(result.getTransactionId());
    setTransactionCode(result.getTransactionCode());
    setDate(result.getDate());
    setTime(result.getTime());
    setTerminalSerialNumber(result.getTerminalSerialNumber());
    setMessage(result.getMessage());
    setErrorMessage(result.getMessage());
    setPaymentType(result.getPaymentType());
    this.isBuildResponse = 1;
  }
}
