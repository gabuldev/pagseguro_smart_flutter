package dev.gabul.pagseguro_smart_flutter.core;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagTransactionResult;
import com.google.gson.Gson;

public class ActionResult {

  int eventCode;

  int isBuildResponse = 0;

  String response;

  public int getBuildResponse() {
    if (isBuildResponse != 0) {
      int response = this.isBuildResponse;
      this.isBuildResponse = 0;
      return response;
    }
    return 0;
  }

  public int getEventCode() {
    return eventCode;
  }

  public void setEventCode(int eventCode) {
    this.eventCode = eventCode;
  }  

  private String mode = "RESPONSE_PAGSEGURO";
  //https://pagseguro.github.io/pagseguro-sdk-plugpagservicewrapper/-wrapper-p-p-s/br.com.uol.pagseguro.plugpagservice.wrapper/-plug-pag-transaction-result/index.html
  private String message; 
  private String errorCode; 
  private String transactionCode; 
  private String transactionId; 
  private String date; 
  private String time; 
  private String hostNsu; 
  private String cardBrand; 
  private String bin; 
  private String holder; 
  private String userReference; 
  private String terminalSerialNumber; 
  private String amount; 
  private String availableBalance; 
  private String cardApplication; 
  private String label; 
  private String holderName; 
  private String extendedHolderName; 
  private int result;
  private String readerModel; 
  private String nsu; 
  private String autoCode; 
  private Character installments; 
  private int originalAmount;
  private String buyerName; 
  private int paymentType;
  private String typeTransaction; 
  private String appIdentification; 
  private String cardHash; 
  private String preAutoDueDate; 
  private String preAutoOriginalAmount; 
  private int userRegistered; 
  private String accumulatedValue; 
  private String consumerIdentification; 
  private String currentBalance; 
  private String consumerPhoneNumber; 
  private String clubePagScreensIds; 
  private String partialPayPartiallyAuthorizedAmount; 
  private String partialPayRemainingAmount; 

  public String getMessage() {
      return message;
  }

  public void setMessage(String message) {
      this.message = message;
  }

  public String getErrorCode() {
      return errorCode;
  }

  public void setErrorCode(String errorCode) {
      this.errorCode = errorCode;
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

  public String getDate() {
      return date;
  }

  public void setDate(String date) {
      this.date = date;
  }

  public String getTime() {
      return time;
  }

  public void setTime(String time) {
      this.time = time;
  }

  public String getHostNsu() {
      return hostNsu;
  }

  public void setHostNsu(String hostNsu) {
      this.hostNsu = hostNsu;
  }

  public String getCardBrand() {
      return cardBrand;
  }

  public void setCardBrand(String cardBrand) {
      this.cardBrand = cardBrand;
  }

  public String getBin() {
      return bin;
  }

  public void setBin(String bin) {
      this.bin = bin;
  }

  public String getHolder() {
      return holder;
  }

  public void setHolder(String holder) {
      this.holder = holder;
  }

  public String getUserReference() {
      return userReference;
  }

  public void setUserReference(String userReference) {
      this.userReference = userReference;
  }

  public String getTerminalSerialNumber() {
      return terminalSerialNumber;
  }

  public void setTerminalSerialNumber(String terminalSerialNumber) {
      this.terminalSerialNumber = terminalSerialNumber;
  }

  public String getAmount() {
      return amount;
  }

  public void setAmount(String amount) {
      this.amount = amount;
  }

  public String getAvailableBalance() {
      return availableBalance;
  }

  public void setAvailableBalance(String availableBalance) {
      this.availableBalance = availableBalance;
  }

  public String getCardApplication() {
      return cardApplication;
  }

  public void setCardApplication(String cardApplication) {
      this.cardApplication = cardApplication;
  }

  public String getLabel() {
      return label;
  }

  public void setLabel(String label) {
      this.label = label;
  }

  public String getHolderName() {
      return holderName;
  }

  public void setHolderName(String holderName) {
      this.holderName = holderName;
  }

  public String getExtendedHolderName() {
      return extendedHolderName;
  }

  public void setExtendedHolderName(String extendedHolderName) {
      this.extendedHolderName = extendedHolderName;
  }

  public int getResult() {
      return result;
  }

  public void setResult(int result) {
      this.result = result;
  }

  public String getReaderModel() {
      return readerModel;
  }

  public void setReaderModel(String readerModel) {
      this.readerModel = readerModel;
  }

  public String getNsu() {
      return nsu;
  }

  public void setNsu(String nsu) {
      this.nsu = nsu;
  }

  public String getAutoCode() {
      return autoCode;
  }

  public void setAutoCode(String autoCode) {
      this.autoCode = autoCode;
  }

  public Character getInstallments() {
      return installments;
  }

  public void setInstallments(Character installments) {
      this.installments = installments;
  }

  public int getOriginalAmount() {
      return originalAmount;
  }

  public void setOriginalAmount(int originalAmount) {
      this.originalAmount = originalAmount;
  }

  public String getBuyerName() {
      return buyerName;
  }

  public void setBuyerName(String buyerName) {
      this.buyerName = buyerName;
  }

  public int getPaymentType() {
      return paymentType;
  }

  public void setPaymentType(int paymentType) {
      this.paymentType = paymentType;
  }

  public String getTypeTransaction() {
      return typeTransaction;
  }

  public void setTypeTransaction(String typeTransaction) {
      this.typeTransaction = typeTransaction;
  }

  public String getAppIdentification() {
      return appIdentification;
  }

  public void setAppIdentification(String appIdentification) {
      this.appIdentification = appIdentification;
  }

  public String getCardHash() {
      return cardHash;
  }

  public void setCardHash(String cardHash) {
      this.cardHash = cardHash;
  }

  public String getPreAutoDueDate() {
      return preAutoDueDate;
  }

  public void setPreAutoDueDate(String preAutoDueDate) {
      this.preAutoDueDate = preAutoDueDate;
  }

  public String getPreAutoOriginalAmount() {
      return preAutoOriginalAmount;
  }

  public void setPreAutoOriginalAmount(String preAutoOriginalAmount) {
      this.preAutoOriginalAmount = preAutoOriginalAmount;
  }

  public int getUserRegistered() {
      return userRegistered;
  }

  public void setUserRegistered(int userRegistered) {
      this.userRegistered = userRegistered;
  }

  public String getAccumulatedValue() {
      return accumulatedValue;
  }

  public void setAccumulatedValue(String accumulatedValue) {
      this.accumulatedValue = accumulatedValue;
  }

  public String getConsumerIdentification() {
      return consumerIdentification;
  }

  public void setConsumerIdentification(String consumerIdentification) {
      this.consumerIdentification = consumerIdentification;
  }

  public String getCurrentBalance() {
      return currentBalance;
  }

  public void setCurrentBalance(String currentBalance) {
      this.currentBalance = currentBalance;
  }

  public String getConsumerPhoneNumber() {
      return consumerPhoneNumber;
  }

  public void setConsumerPhoneNumber(String consumerPhoneNumber) {
      this.consumerPhoneNumber = consumerPhoneNumber;
  }

  public String getClubePagScreensIds() {
      return clubePagScreensIds;
  }

  public void setClubePagScreensIds(String clubePagScreensIds) {
      this.clubePagScreensIds = clubePagScreensIds;
  }

  public String getPartialPayPartiallyAuthorizedAmount() {
      return partialPayPartiallyAuthorizedAmount;
  }

  public void setPartialPayPartiallyAuthorizedAmount(String partialPayPartiallyAuthorizedAmount) {
      this.partialPayPartiallyAuthorizedAmount = partialPayPartiallyAuthorizedAmount;
  }

  public String getPartialPayRemainingAmount() {
      return partialPayRemainingAmount;
  }

  public void setPartialPayRemainingAmount(String partialPayRemainingAmount) {
      this.partialPayRemainingAmount = partialPayRemainingAmount;
  }  


  public void buildResponse(PlugPagTransactionResult result) {
    setMessage(result.getMessage());
    setErrorCode(result.getErrorCode());
    setTransactionCode(result.getTransactionCode());
    setTransactionId(result.getTransactionId());
    setDate(result.getDate());
    setTime(result.getTime());
    setHostNsu(result.getHostNsu());
    setCardBrand(result.getCardBrand());
    setBin(result.getBin());
    setHolder(result.getHolder());
    setUserReference(result.getUserReference());
    setTerminalSerialNumber(result.getTerminalSerialNumber());
    setAmount(result.getAmount());
    setAvailableBalance(result.getAvailableBalance());
    setCardApplication(result.getCardApplication());
    setLabel(result.getLabel());
    setHolderName(result.getHolderName());
    setExtendedHolderName(result.getExtendedHolderName());
    setResult(result.getResult());
    setReaderModel(result.getReaderModel());
    setNsu(result.getNsu());
    setAutoCode(result.getAutoCode());
    setInstallments(result.getInstallments());
    setOriginalAmount(result.getOriginalAmount());
    setBuyerName(result.getBuyerName());
    setPaymentType(result.getPaymentType());
    setTypeTransaction(result.getTypeTransaction());
    setAppIdentification(result.getAppIdentification());
    setCardHash(result.getCardHash());
    setPreAutoDueDate(result.getPreAutoDueDate());
    setPreAutoOriginalAmount(result.getPreAutoOriginalAmount());
    setUserRegistered(result.getUserRegistered());
    setAccumulatedValue(result.getAccumulatedValue());
    setConsumerIdentification(result.getConsumerIdentification());
    setCurrentBalance(result.getCurrentBalance());
    setConsumerPhoneNumber(result.getConsumerPhoneNumber());
    setClubePagScreensIds(result.getClubePagScreensIds());
    setPartialPayPartiallyAuthorizedAmount(result.getPartialPayPartiallyAuthorizedAmount());
    setPartialPayRemainingAmount(result.getPartialPayRemainingAmount());
    this.isBuildResponse = 1;
  }
}
