package com.hanwha.backend.util.converter;

import com.hanwha.backend.data.IData;

public interface Converter {
    IData convert(IData data);
}
