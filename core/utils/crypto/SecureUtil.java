package com.mtrsz.dpms.common.utils.crypto;

import com.mtrsz.dpms.common.constant.CryptoAlgorithm;
import com.mtrsz.dpms.common.utils.crypto.codec.MD5;

import java.io.File;
import java.io.InputStream;

/**
 * 安全相关工具类<br>
 * 加密分为三种：<br>
 * 1、对称加密（symmetric），例如：AES、DES等<br>
 * 2、非对称加密（asymmetric），例如：RSA、DSA等<br>
 * 3、摘要加密（digest），例如：MD5、SHA-1、SHA-256、HMAC等<br>
 *
 * @author Looly, Gsealy
 */
public class SecureUtil {

	// ------------------------------------------------------------------- 摘要算法

	/**
	 * MD5加密<br>
	 * 例：
	 *
	 * <pre>
	 * MD5加密：md5().digest(data)
	 * MD5加密并转为16进制字符串：md5().digestHex(data)
	 * </pre>
	 *
	 * @return {@link CipherExecutor}
	 */
	public static MD5 md5() {
		return new MD5();
	}

	/**
	 * MD5加密，生成16进制MD5字符串<br>
	 *
	 * @param data 数据
	 * @return MD5字符串
	 */
	public static String md5(String data) {
		return new MD5().digestHex(data);
	}

	/**
	 * MD5加密，生成16进制MD5字符串<br>
	 *
	 * @param data 数据
	 * @return MD5字符串
	 */
	public static String md5(InputStream data) {
		return new MD5().digestHex(data);
	}

	/**
	 * MD5加密文件，生成16进制MD5字符串<br>
	 *
	 * @param dataFile 被加密文件
	 * @return MD5字符串
	 */
	public static String md5(File dataFile) {
		return new MD5().digestHex(dataFile);
	}

	/**
	 * SHA1加密<br>
	 * 例：<br>
	 * SHA1加密：sha1().digest(data)<br>
	 * SHA1加密并转为16进制字符串：sha1().digestHex(data)<br>
	 *
	 * @return {@link CipherExecutor}
	 */
	public static CipherExecutor sha1() {
		return new CipherExecutor(CryptoAlgorithm.SHA1);
	}

	/**
	 * SHA1加密，生成16进制SHA1字符串<br>
	 *
	 * @param data 数据
	 * @return SHA1字符串
	 */
	public static String sha1(String data) {
		return new CipherExecutor(CryptoAlgorithm.SHA1).digestHex(data);
	}

	/**
	 * SHA1加密，生成16进制SHA1字符串<br>
	 *
	 * @param data 数据
	 * @return SHA1字符串
	 */
	public static String sha1(InputStream data) {
		return new CipherExecutor(CryptoAlgorithm.SHA1).digestHex(data);
	}

	/**
	 * SHA1加密文件，生成16进制SHA1字符串<br>
	 *
	 * @param dataFile 被加密文件
	 * @return SHA1字符串
	 */
	public static String sha1(File dataFile) {
		return new CipherExecutor(CryptoAlgorithm.SHA1).digestHex(dataFile);
	}

	/**
	 * SHA256加密<br>
	 * 例：<br>
	 * SHA256加密：sha256().digest(data)<br>
	 * SHA256加密并转为16进制字符串：sha256().digestHex(data)<br>
	 *
	 * @return {@link CipherExecutor}
	 * @since 4.3.2
	 */
	public static CipherExecutor sha256() {
		return new CipherExecutor(CryptoAlgorithm.SHA256);
	}

	/**
	 * SHA256加密，生成16进制SHA256字符串<br>
	 *
	 * @param data 数据
	 * @return SHA256字符串
	 * @since 4.3.2
	 */
	public static String sha256(String data) {
		return new CipherExecutor(CryptoAlgorithm.SHA256).digestHex(data);
	}

	/**
	 * SHA256加密，生成16进制SHA256字符串<br>
	 *
	 * @param data 数据
	 * @return SHA1字符串
	 * @since 4.3.2
	 */
	public static String sha256(InputStream data) {
		return new CipherExecutor(CryptoAlgorithm.SHA256).digestHex(data);
	}

	/**
	 * SHA256加密文件，生成16进制SHA256字符串<br>
	 *
	 * @param dataFile 被加密文件
	 * @return SHA256字符串
	 * @since 4.3.2
	 */
	public static String sha256(File dataFile) {
		return new CipherExecutor(CryptoAlgorithm.SHA256).digestHex(dataFile);
	}

}
