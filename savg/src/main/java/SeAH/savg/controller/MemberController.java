package SeAH.savg.controller;
import SeAH.savg.dto.response.Response;
import SeAH.savg.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@RequestParam(value = "사용자 id", required = true) @PathVariable Long id) {
        return Response.success(memberService.read(id));
    }


    @DeleteMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@RequestParam(value = "사용자 id", required = true) @PathVariable Long id) {
        memberService.delete(id);
        return Response.success();
    }
}