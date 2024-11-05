package org.tenten.bittakotlin.jobpost.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/job-post")
class JobPostViewController {
    @GetMapping
    fun showJobpostPage(model: Model?): String {
        return "jobpost/jobpost"
    }
}

