<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Minigames</title>
  <link href="style.css" rel="stylesheet">
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<ul>
  <li id = "one">first</li>
  <li id = "two">second</li>
  <li id = "three">third</li>
</ul>

<div class = "board">
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
  <div class="box"></div>
</div>

<br>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>

<script>
  const element = document.querySelectorAll("li");
  element.forEach(thing => {
    thing.addEventListener("click", ()=> {
      console.log("event logged " + thing.id)

    })
  })
  const body = document.body
  const header = document.querySelector("h1")
  header.addEventListener("dblclick", () => {
    console.log("header clicked on")
    body.append("hi ")
  })
</script>