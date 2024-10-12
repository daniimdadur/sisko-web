package practice.web.fakultas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import practice.web.base.Response;
import practice.web.fakultas.model.FakultasReq;
import practice.web.fakultas.model.FakultasRes;
import practice.web.fakultas.service.FakultasService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fakultas")
public class FakultasController {
    private final FakultasService fakultasService;

    @GetMapping
    public ModelAndView get() {
        ModelAndView mav = new ModelAndView("pages/master/fakultas/index");

        List<FakultasRes> result = this.fakultasService.get();
        mav.addObject("data", result);
        return mav;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView get(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("pages/master/fakultas/detail");

        Optional<FakultasRes> result = this.fakultasService.getById(id);
        if (result.isPresent()) {
            mav.addObject("fakultas", result.get());
            return mav;
        }

        return new ModelAndView("pages/auth/error-404");
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView("pages/master/fakultas/add");

        FakultasReq request = new FakultasReq();
        mav.addObject("fakultas", request);
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute FakultasReq request) {
        fakultasService.save(request);
        return new ModelAndView("redirect:/fakultas");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("pages/master/fakultas/edit");

        Optional<FakultasRes> result = fakultasService.getById(id);
        if (result.isPresent()) {
            mav.addObject("fakultas", result.get());
            return mav;
        }

        return new ModelAndView("pages/auth/error-404");
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute FakultasReq request) {
        fakultasService.update(request, request.getId());
        return new ModelAndView("redirect:/fakultas");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("pages/master/fakultas/delete");
        FakultasRes result = fakultasService.getById(id).orElse(null);
        if (result != null) {
            mav.addObject("fakultas", result);
            return mav;
        }

        return new ModelAndView("pages/auth/error-404");
    }

    @PostMapping("/remove")
    public ModelAndView remove(@ModelAttribute FakultasReq request) {
        fakultasService.delete(request.getId());
        return new ModelAndView("redirect:/fakultas");
    }

//    @GetMapping("/data")
//    public ResponseEntity<Response> data(){
//        List<FakultasRes> result = fakultasService.get();
//        return ResponseEntity.ok().body(
//                Response.builder()
//                        .status(HttpStatus.OK.value())
//                        .message("Success")
//                        .data(result)
//                        .total(result.size())
//                        .build()
//        );
//    }
}
