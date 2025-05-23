package com.ssafy.ws.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

public class ImageUtil {

	public static String convertImageBytesToBase64(byte[] imageBytes) {
		if (imageBytes == null || imageBytes.length == 0) {
			return null;
		}

		String mimeType = "image/jpeg";
		try {
			mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
			if (mimeType == null) {
				String str = new String(imageBytes);
				if (str.trim().startsWith("<svg")) {
					mimeType = "image/svg+xml";
				}
			}
		} catch (IOException e) {
			return null;
		}
		return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
	}
}
