package br.com.oncorp.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;

public final class Utilitarios {

	private Utilitarios() {

	}

	public static Connection getConnection(String jndi) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf(jndi);

		return broker.getConnection();

	}

	public static void closeConnection(Connection con) {

		try {

			con.close();

		} catch (SQLException ex) {

			ex.printStackTrace();

		}

	}

	public static void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {

			arquivo = Constantes.PASTA_ARQUIVOS + arquivo;

			fos = new FileOutputStream(arquivo);

			fos.write(bytes);

			fos.close();

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String tratarString(String valor) {
		if (!TSUtil.isEmpty(valor)) {
			valor = valor.trim().replaceAll("  ", " ");
		}

		return valor;
	}

	public static Integer tratarInteger(Integer valor) {

		if (!TSUtil.isEmpty(valor) && valor.equals(0)) {

			valor = null;

		}

		return valor;

	}

	public static Long tratarLong(Long valor) {
		if ((!TSUtil.isEmpty(valor)) && (valor.equals(Long.valueOf(0L)))) {
			valor = null;
		}

		return valor;
	}

	public static BigDecimal tratarDouble(BigDecimal valor) {

		if (!TSUtil.isEmpty(valor) && valor.equals(0D)) {

			valor = null;

		}

		if (!TSUtil.isEmpty(valor) && valor.equals(new BigDecimal("0.00"))) {

			valor = null;

		}

		return valor;

	}

	public static String removerAcentos(String palavra) {
		if (TSUtil.isEmpty(palavra)) {
			return null;
		}

		return Normalizer.normalize(new StringBuilder(palavra), Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	public static String gerarHash(String valor) {
		if (!TSUtil.isEmpty(valor)) {
			valor = TSCryptoUtil.gerarHash(valor, "md5");
		}

		return valor;
	}

	public static String gerarSenha() {
		Calendar rightNow = Calendar.getInstance();

		int diaAtual = rightNow.get(5);
		int mesAtual = rightNow.get(2) + 1;
		int anoAtual = rightNow.get(1);
		int horaAtual = rightNow.get(11);
		int minutoAtual = rightNow.get(12);
		int segAtual = rightNow.get(13);
		int miliAtual = rightNow.get(14);

		String senha = anoAtual + mesAtual + diaAtual + horaAtual + minutoAtual + segAtual + miliAtual + "";

		return senha;
	}

	public static String getSituacao(Boolean flagAtivo) {
		if ((!TSUtil.isEmpty(flagAtivo)) && (flagAtivo.booleanValue())) {
			return "Ativo";
		}

		return "Inativo";
	}

	public static String getDiaMesAno(Date data) {

		if (!TSUtil.isEmpty(data)) {
			Calendar calendar = Calendar.getInstance(Locale.getDefault());
			calendar.setTime(data);
			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			int mes = calendar.get(Calendar.MONTH);
			mes = mes + 1;
			String diaMesAno = "" + dia;
			if (mes < 10) {
				diaMesAno = diaMesAno + "0";
			}
			diaMesAno = diaMesAno + "_" + mes + "_" + calendar.get(Calendar.YEAR) + "_";
			return diaMesAno;

		}
		return null;
	}

	public static String removeMask(String cpf) {

		String str = cpf;

		if (!TSUtil.isEmpty(cpf)) {

			while (str.indexOf(")") != -1) {
				if (str.indexOf(")") != 0) {
					str = str.substring(0, str.indexOf(")")) + str.substring(str.indexOf(")") + 1);
				} else {
					str = str.substring(str.indexOf(")") + 1);
				}
			}

			while (str.indexOf("(") != -1) {
				if (str.indexOf("(") != 0) {
					str = str.substring(0, str.indexOf("(")) + str.substring(str.indexOf("(") + 1);
				} else {
					str = str.substring(str.indexOf("(") + 1);
				}
			}

			while (str.indexOf("/") != -1) {
				if (str.indexOf("/") != 0) {
					str = str.substring(0, str.indexOf("/")) + str.substring(str.indexOf("/") + 1);
				} else {
					str = str.substring(str.indexOf("/") + 1);
				}
			}

			while (str.indexOf("-") != -1) {
				if (str.indexOf("-") != 0) {
					str = str.substring(0, str.indexOf("-")) + str.substring(str.indexOf("-") + 1);
				} else {
					str = str.substring(str.indexOf("-") + 1);
				}
			}
			while (str.indexOf(".") != -1) {
				if (str.indexOf(".") != 0) {
					str = str.substring(0, str.indexOf(".")) + str.substring(str.indexOf(".") + 1);
				} else {
					str = str.substring(str.indexOf(".") + 1);
				}
			}
		}
		return str;
	}

	public static String funcaoHoras(int isst) {

		int iss = isst % 60;
		isst /= 60;
		int imin = isst % 60;
		isst /= 60;
		int ihh = isst % 24;

		return strzero(ihh) + ":" + strzero(imin) + ":" + strzero(iss);
	}

	private static String strzero(int n) {

		if (n < 10) {

			return "0" + String.valueOf(n);
		}

		return String.valueOf(n);
	}

}