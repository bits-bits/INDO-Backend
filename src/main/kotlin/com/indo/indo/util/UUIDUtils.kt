package com.indo.indo.util

import java.lang.IllegalArgumentException
import java.util.UUID

class UUIDUtils {
	companion object Utils {
		fun isValidUUID(uuid: String): Boolean {
			try {
				UUID.fromString(uuid);
				return true;
			} catch (e: IllegalArgumentException) {
				return false;
			}
		}
	}
}