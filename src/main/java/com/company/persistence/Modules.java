package com.company.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Modules {
    HORNET("F18"),
    F16("F16"),
    A10C("A10C"),
    F15_EAGLE("F15C Eagle"),
    SU33("SU33"),
    MIG29("MIG29"),
    F14("F14");


    private final String moduleName;

}
