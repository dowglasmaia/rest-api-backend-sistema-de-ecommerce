package com.maia.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.PgtoBoleto;

@Service
public class BoletoService {

	public void preencherPgtoBoleto(PgtoBoleto pgtBoleto, Date dtaPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dtaPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7); // Sete Dias
		pgtBoleto.setDtaPagamento(cal.getTime());

	}

}
