<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="bootstrap.min.css"/>
    <link rel="stylesheet" href="style.css"/>
    <title>TEXT2KWIC</title>
</head>

<body class="container-fluid m-0 p-0">
<header class="header text-light ">
    <h6 class="m-0">TEXT2KWIC</h6>
</header>
<hr style="margin:0 "/>
<div class="row p-2" style="height: 50px;">
    <div class="col-6 d-flex justify-content-between align-items-center">
        <small class="pl-5 d-block text-muted">PLAIN TEXT</small>
        <div>
            <button id="clear" class="btn btn-outline-info btn-sm mr-3 ">CLEAR</button>
            <button id="submit" class="btn btn-success btn-sm mr-4 ">CONVERT</button>
        </div>
    </div>
    <div class="col-6 d-flex justify-content-between align-items-center">
        <small class="text-muted">PREVIEW</small>
    </div>
</div>
<hr style="margin:0 "/>
<div class="row mt-0">
    <div class="col-6">
        <div class="wpb-wrapper">
            <div class="line-number-bg"></div>
            <div class="wrapper">
                <div id="code" contenteditable>
                    <div class="line">&nbsp;</div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-6">
        <div class="wpb-wrapper">
            <div class="wrapper">
                <div class="spinner-container hidden" id="loader">
                    <div class="spinner">
                        <div class="cube1"></div>
                        <div class="cube2"></div>
                    </div>
                </div>
                <div id="preview"></div>
            </div>
        </div>
    </div>
</div>
<div id="alert"></div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

<script src="index.js"></script>
</body>
</html>
