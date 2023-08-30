package SeAH.savg.jwt;

import SeAH.savg.service.AuthService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import static SeAH.savg.jwt.TokenStatus.StatusCode.*;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TokenStatus {

    public enum StatusCode {
        OK,
        EXPIRED,
        REFRESH_EXPIRED,
        UNAUTHORIZED,
        UNKNOWN
    }

    private StatusCode statusCode;

    public static TokenStatus of(StatusCode status) {
        return makeTokenStatus(status);
    }

    public static TokenStatus makeTokenStatus(StatusCode status) {
        if (OK.equals(status)) {
            return new TokenStatus(OK);
        }
        if (UNAUTHORIZED.equals(status)) {
            return new TokenStatus(UNAUTHORIZED);
        }
        if (EXPIRED.equals(status)) {

            return new TokenStatus(EXPIRED);
        }
        return new TokenStatus(UNKNOWN);
    }
}