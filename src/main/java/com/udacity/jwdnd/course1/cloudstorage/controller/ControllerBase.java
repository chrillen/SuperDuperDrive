package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerBase {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
