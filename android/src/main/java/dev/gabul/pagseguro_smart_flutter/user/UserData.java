package dev.gabul.pagseguro_smart_flutter.user;

import java.io.Serializable;
import java.util.Objects;

public class UserData implements Serializable {


    private String value;
    private String name;
    private String cpf;
    private String numberTag;
    private String cellPhone;
    private String active;
    private String idEvento;
    private String openValue;



    public UserData() {
    }

    public UserData(String value, String name, String cpf, String numberTag, String cellPhone, String active, String idEvento, String openValue) {

        this.value = value;
        this.name = name;
        this.cpf = cpf;
        this.numberTag = numberTag;
        this.cellPhone = cellPhone;
        this.active = active;
        this.idEvento = idEvento;
        this.openValue = openValue;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumberTag() {
        return numberTag;
    }

    public void setNumberTag(String numberTag) {
        this.numberTag = numberTag;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getActive()
    {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getOpenValue() {
        return openValue;
    }

    public void setOpenValue(String openValue) {
        this.openValue = openValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData nfcData = (UserData) o;
        return  Objects.equals(getValue(), nfcData.getValue()) &&
                Objects.equals(getName(), nfcData.getName()) &&
                Objects.equals(getCpf(), nfcData.getCpf()) &&
                Objects.equals(getNumberTag(), nfcData.getNumberTag()) &&
                Objects.equals(getCellPhone(), nfcData.getCellPhone()) &&
                Objects.equals(getActive(), nfcData.getActive()) &&
                Objects.equals(getIdEvento(), nfcData.getIdEvento()) &&
                Objects.equals(getOpenValue(), nfcData.getOpenValue());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getName(), getCpf(), getNumberTag(), getCellPhone(), getActive(), getIdEvento(), getOpenValue());
    }

    @Override
    public String toString() {
        return "UserData{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", numberTag='" + numberTag + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", active='" + active + '\'' +
                ", idEvento='" + idEvento + '\'' +
                ", openValue='" + openValue + '\'' +
                '}';
    }
}
