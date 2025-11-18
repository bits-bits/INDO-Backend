package com.indo.indo.util

import java.util.UUID

class UUIDUtils {
	companion object Utils {
		fun getUuidOrNull(id: String): UUID? {
			return try {
				UUID.fromString(id);
			} catch (e: kotlin.IllegalArgumentException) {
				null;
			}
		}
	}
}