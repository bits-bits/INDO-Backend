package com.indo.indo.util

import java.lang.IllegalArgumentException
import java.util.UUID

class UUIDUtils {
	companion object Utils {
		fun getUUIDOrNull(uuid: String): UUID? {
			return try {
				UUID.fromString(uuid);
			} catch (e: kotlin.IllegalArgumentException) {
				null;
			}
		}
	}
}