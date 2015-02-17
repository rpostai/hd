package com.rp.hd.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.regex.Pattern;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Instant;

import com.auth0.jwt.JWTVerifier;
import com.google.gson.JsonObject;

/***
 * Classe responsável por produzir um JWT token e confirmar a validade do mesmo
 * 
 * @author rodrigo.postai
 *
 */

public class SecurityTokenUtils {

	private static final String AUDIENCE = "HD";
	private static final String ISSUER = "HD";

	public static final String CHAVE_AUTORIZACAO = "Authorization";

	public static final int SEGUNDOS_POR_MINUTO = 60;

	/***
	 * Gera um token codificado com base64 e criptografado com o algorimot SHA-256
	 * Este token é utilizado para a validaćão de usuário nas interaćões entre o browser e o servidor
	 * 
	 * @param dominio  dominio no qual a aplicaćão será hospedada
	 * @param login    login do usuário autenticado
	 * @param nome     nome do usuário autenticado
	 * @param validade quantidade de minutos no qual o token continuará válido
	 * @return
	 */
	public static String criarJWTToken(String dominio, String login,
			String nome, Integer validade) {

		HmacSHA256Signer signer;
		try {
			// ATENCAO
			// A chave secreta deve ser codificada em base64, pois o JWTVerifier
			// o faz no momento da checagem
			// Portanto, na criacao do token, deve codifica-la, porem na
			// checagem, nao, pois o JWTVerifier faz internamente
			signer = new HmacSHA256Signer(ISSUER, AUDIENCE,
					Base64.decodeBase64(SecretKey.KEY));

			JsonToken token = new JsonToken(signer);

			Calendar cal = Calendar.getInstance();
			if (validade != null) {
				token.setExpiration(new Instant(cal.getTimeInMillis()
						+ SEGUNDOS_POR_MINUTO * validade * 1000));
			} else {
			}

			JsonObject tokenJSON = token.getPayloadAsJsonObject();
			tokenJSON.addProperty("domain", dominio);
			tokenJSON.addProperty("user_id", login);
			tokenJSON.addProperty("display_name", nome);

			return token.serializeAndSign();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * Verifica se um determinado token JWT continua valido
	 * 
	 * @param token
	 * @return
	 */
	public static boolean isJWTValido(String token) {
		String somenteToken = getToken(token);
		if (somenteToken != null) {
			JWTVerifier verificador = new JWTVerifier(SecretKey.KEY);
			try {
				verificador.verify(somenteToken);
				return true;
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| IllegalStateException | SignatureException | IOException e) {
				return false;
			}	
		}
		return false;
	}

	/***
	 * O token deve ser recebido no seguinte formato: Bearer 8948892Y89HHSDHFAH
	 * @param token
	 * @return
	 */
	private static String getToken(String token) {
		if (StringUtils.isNotBlank(token)) {
			String[] parts = token.split(" ");

			String scheme = parts[0];
			String credentials = parts[1];

			Pattern pattern = Pattern.compile("^Bearer$",
					Pattern.CASE_INSENSITIVE);
			if (pattern.matcher(scheme).matches()) {
				token = credentials;
			}
			return token;
		}
		return null;
	}

}
