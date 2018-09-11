package com.dimeng.p2p.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.sql.SQLException;
import java.util.Date;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.service.PtAccountManage;

/**
 * RSA算法加密/解密工具类。
 *
 * @author tonglei
 * @version 1.0.0, 2015-05-14
 */
public abstract class RSAUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);

    /** 算法名称 */
    private static final String ALGORITHOM = "RSA";
    /** 密钥大小 */
    private static final int KEY_SIZE = 1024;
    /** 默认的安全服务提供者 */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

    private static KeyPairGenerator keyPairGen = null;
    private static KeyFactory keyFactory = null;
    /** 缓存的密钥对。 */
    private static KeyPair oneKeyPair = null;

    static
    {
        try
        {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        }
        catch (NoSuchAlgorithmException ex)
        {
            LOGGER.error(ex.getMessage());
        }
    }

    private RSAUtils() {
    }

    /**
     * 生成并返回RSA密钥对。
     */
    private static KeyPair generateKeyPair(ResourceProvider resourceProviderParam)
    {
        try {
            keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes("UTF-8")));
            oneKeyPair = keyPairGen.generateKeyPair();
            return oneKeyPair;
        } catch (InvalidParameterException ex) {
            LOGGER.error("KeyPairGenerator does not support a key length of " + KEY_SIZE + ".", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_PAIR_GEN is null, can not generate KeyPairGenerator instance.",
                    ex);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回RSA密钥对。
     */
    public static KeyPair getKeyPair(ResourceProvider resourceProviderParam)
    {
        // 直接强制生成密钥对文件，并存入数据库。
        return generateKeyPair(resourceProviderParam);
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     *
     * @param modulus 系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("RSAPublicKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
        return null;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
     *
     * @param modulus 系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("RSAPrivateKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
        return null;
    }

    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
     *
     * @param hexModulus 系数。
     * @param hexPrivateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPrivateExponent)) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
            }
            return null;
        }
        byte[] modulus = null;
        byte[] privateExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
        } catch(DecoderException ex) {
            LOGGER.error("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
        }
        if(modulus != null && privateExponent != null) {
            return generateRSAPrivateKey(modulus, privateExponent);
        }
        return null;
    }

    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
     *
     * @param hexModulus 系数。
     * @param hexPublicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPublicExponent)) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
            }
            return null;
        }
        byte[] modulus = null;
        byte[] publicExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
        } catch(DecoderException ex) {
            LOGGER.error("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
        }
        if(modulus != null && publicExponent != null) {
            return generateRSAPublicKey(modulus, publicExponent);
        }
        return null;
    }

    /**
     * 使用指定的公钥加密数据。
     *
     * @param publicKey 给定的公钥。
     * @param data 要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    /**
     * 使用指定的私钥解密数据。
     *
     * @param privateKey 给定的私钥。
     * @param data 要解密的数据。
     * @return 原数据。
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        return ci.doFinal(data);
    }

    /**
     * 使用给定的公钥加密给定的字符串。
     * <p />
     * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null} 则返回 {@code
     * null}。
     *
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(PublicKey publicKey, String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        try {
            byte[] data = plaintext.getBytes("UTF-8");
            byte[] en_data = encrypt(publicKey, data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
    }

    /**
     * 使用默认的公钥加密给定的字符串。
     * <p />
     * 若{@code plaintext} 为 {@code null} 则返回 {@code null}。
     *
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(String plaintext)
    {
        if(plaintext == null) {
            return null;
        }
        byte[] data = new byte[0];
        try {
            ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
            ServiceProvider serviceProvider = resourceProvider.getResource(ServiceProvider.class);
            try(ServiceSession serviceSession = serviceProvider.createServiceSession()){
                PtAccountManage ptAccountManage = serviceSession.getService(PtAccountManage.class);
                data = plaintext.getBytes("UTF-8");
                byte[] en_data = encrypt(ptAccountManage.getPublicKey(), data);
                return new String(Hex.encodeHex(en_data));
            }

        } catch(NullPointerException ex) {
            LOGGER.error("keyPair cannot be null.");
        } catch(Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
    }

    /**
     * 使用给定的私钥解密给定的字符串。
     * <p />
     * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     *
     * @param privateKey 给定的私钥。
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(PrivateKey privateKey, String encrypttext) {
        if (privateKey == null || StringUtils.isBlank(encrypttext)) {
            return null;
        }
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data,"UTF-8");
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
        }
        return null;
    }

    /**
     * 使用默认的私钥解密给定的字符串。
     * <p />
     * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     *
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(String encrypttext)
    {
        if(StringUtils.isBlank(encrypttext)) {
            return null;
        }
        try {
            ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
            ServiceProvider serviceProvider = resourceProvider.getResource(ServiceProvider.class);
            try(ServiceSession serviceSession = serviceProvider.createServiceSession()){
                PtAccountManage ptAccountManage = serviceSession.getService(PtAccountManage.class);
                byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
                byte[] data = decrypt(ptAccountManage.getPrivateKey(), en_data);
                return new String(data,"UTF-8");
            }

        } catch(NullPointerException ex) {
            LOGGER.error("keyPair cannot be null.");
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getMessage()));
        }
        return null;
    }

    /**
     * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
     *
     * @param encrypttext 密文。
     * @return {@code encrypttext} 的原文字符串。
     * @throws Exception
     * @throws SQLException
     */
    public static String decryptStringByJs(String encrypttext)
            throws Exception
    {
        if (StringUtils.isBlank(encrypttext))
        {
            return null;
        }
        ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
        ServiceProvider serviceProvider = resourceProvider.getResource(ServiceProvider.class);
        try(ServiceSession serviceSession = serviceProvider.createServiceSession()){
            PtAccountManage ptAccountManage = serviceSession.getService(PtAccountManage.class);
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(ptAccountManage.getPrivateKey(), en_data);
            return StringUtils.reverse(new String(data, "UTF-8"));
        }

    }

    /** 返回已初始化的默认的公钥。
     * @throws Exception
     * @throws SQLException */
    public static RSAPublicKey getDefaultPublicKey()
            throws SQLException, Exception
    {
        ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
        ServiceProvider serviceProvider = resourceProvider.getResource(ServiceProvider.class);
        try(ServiceSession serviceSession = serviceProvider.createServiceSession()){
            PtAccountManage ptAccountManage = serviceSession.getService(PtAccountManage.class);
            return ptAccountManage.getPublicKey();
        }

    }

    /** 返回已初始化的默认的私钥。
     * @throws Exception
     * @throws SQLException */
    public static RSAPrivateKey getDefaultPrivateKey()
            throws Exception
    {
        ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
        ServiceProvider serviceProvider = resourceProvider.getResource(ServiceProvider.class);
        try(ServiceSession serviceSession = serviceProvider.createServiceSession()){
            PtAccountManage ptAccountManage = serviceSession.getService(PtAccountManage.class);
            return ptAccountManage.getPrivateKey();
        }

    }

}
