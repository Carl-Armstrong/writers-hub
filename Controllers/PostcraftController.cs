﻿using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace writers_hub.Controllers
{
    public class PostcraftController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}