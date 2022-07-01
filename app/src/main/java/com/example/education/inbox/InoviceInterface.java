package com.example.education.inbox;

import com.example.education.response.InvoiceResonse;

import java.util.List;

public interface InoviceInterface {

    void dismissProgressbar();

    void setData(List<InvoiceResonse> body);
}
