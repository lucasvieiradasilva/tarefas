package br.com.tarefas.lib;

public class MathUtils {
	public static double division(double number, double divisor) {
		if(divisor == 0) {
			return 0;
        }
		return number / divisor;
	}

	public static double division(double number, double divisor, int decimals) {
		return round(division(number, divisor), decimals);
	}

	public static double sum(double value1, double value2) {
		return sum(value1, value2, 0);
	}

	public static double round( double number, int decimals) {
		double d2 = Math.pow( 10D, decimals );
		return(Math.round(number * d2) / d2 );
    }

	public static double truncate(double number, int decimals) {
		double d2 = Math.pow(10D, decimals);
	    return (((int)(round(number * d2, decimals + 6 ))) / d2);
    }

	public static int getInteger(double number) {
		return (int)number;
	}

	public static double getDecimal(double number){
		int integer = (int)number;
		return number - integer;
    }

	public static double sum(double value1, double value2, int decimals) {
		if(decimals == 0) {
			return value1 + value2;
		}
		return round(value1 + value2, decimals);
	}
}
