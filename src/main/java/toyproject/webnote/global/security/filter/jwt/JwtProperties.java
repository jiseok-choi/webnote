package toyproject.webnote.global.security.filter.jwt;

public class JwtProperties {
    public static final String SECRET = "testSecretKey";
    //public static final int EXPIRATION_TIME = 1000 * 60 * 60; // 1 Hour
    public static final long EXPIRATION_TIME = 8640000000L; // 100 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
