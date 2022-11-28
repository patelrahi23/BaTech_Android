package com.example.batech_1.ModelClasses;

public class MachineModelClass {

    String model_number,  Desc, name;


    public MachineModelClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MachineModelClass(String model_number, String type, String name) {
        this.model_number = model_number;
        Desc = type;
        this.name = name;

    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String type) {
        Desc = type;
    }
}
