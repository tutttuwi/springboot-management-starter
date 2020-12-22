package me.tutttuwi.springboot.management.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RegistUserInfoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fstNm;
	private String lstNm;

	// マルチパートファイル操作
	private MultipartFile iconImg;

	//	@AssertTrue(message = "開始日は終了日以前を入力してください。")
	//	public boolean isDateValid() {
	//		if (enddate >= startdate)
	//			return true;
	//		return false;
	//	}
}
