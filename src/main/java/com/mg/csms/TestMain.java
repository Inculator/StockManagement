package com.mg.csms;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.mg.csms.beans.ColdStorage;

public class TestMain {

	public static void main(String[] args) {
		/*ColdStorage coldObject = makeColdObject();
		ColdStorage coldObject1 = makeColdObject1();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("D:\\cold.json");
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
			}

		try {
			Map<Integer, ColdStorage> coldMap = mapper.readValue(file, new TypeReference<Map<Integer, ColdStorage>>() {
			});
			coldMap.put(coldObject.getColdId(), coldObject);
			coldMap.put(coldObject1.getColdId(), coldObject1);
			mapper.writeValue(file, coldMap);

			coldMap = mapper.readValue(file, new TypeReference<Map<Integer, ColdStorage>>() {
			});

			System.out.println(coldMap.size());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		LocalDate endofCentury = LocalDate.of(2018, 12, 01);
		LocalDate now = LocalDate.now();

		System.out.println(ChronoUnit.DAYS.between(now, endofCentury));
		System.out.println((int)Math.ceil((double)ChronoUnit.DAYS.between(now, endofCentury)/30));

	}

	private static ColdStorage makeColdObject() {
		ColdStorage cold = new ColdStorage();
		cold.setColdId(1);
		cold.setColdName("Ambey Cold");
		cold.setAddress("Kundli");
		cold.setDate(LocalDate.now());
		cold.setPhoneNo(8985878545L);

		return cold;
	}

	private static ColdStorage makeColdObject1() {
		ColdStorage cold = new ColdStorage();
		cold.setColdId(4);
		cold.setColdName("Ambey Cold");
		cold.setAddress("Kundli");
		cold.setDate(LocalDate.now());
		cold.setPhoneNo(8985878785L);

		return cold;
	}
}
