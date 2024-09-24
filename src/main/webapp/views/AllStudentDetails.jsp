<%@ page import="com.springboot.swt.project.entity.User" %>

    <%@ page import="com.springboot.swt.project.entity.User,java.util.ArrayList" %>

        <%@ page import="java.nio.charset.StandardCharsets" %>
            <!DOCTYPE html>
            <html>

            <head>
                <%@ include file="component/head.jsp" %>
            </head>

            <body>

                <%@ include file="component/navbar.jsp" %>
                    <div class="py-4">
                        <div class="row">
                            <section class="col py-4">
                                <div class="footer-newsletter">
                                    <div class="container">
                                        <div class="row justify-content-center text-center">
                                            <div class="col-lg-6">
                                                <h1>Active Bactch  Record</h1>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div id="batchesContainer" class="row"></div>
                                <div class="row mt-4">
                                    <div class="col-lg-12  mb-lg-0 mb-4">
                                        <div class="card z-index-2 h-100">
                                            <div class="card-header pb-0 pt-3 bg-transparent">
                                                <h6 class="text-capitalize">completed Batches:-</h6>

                                            </div>
                                            <div class="card-body p-3">

                                                <div class="table-responsive">
                                                    <table class="table align-items-center " id="backtable">

                                                    </table>
                                                    <table class="table align-items-center " id="tableid">
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>

                    <script>
                        let lastClickedButton = null; // Track the last clicked button
                        function changeColor(element) {
                            if (!element) return;
                            if (lastClickedButton) {
                                lastClickedButton.classList.remove('bg-primary');
                                lastClickedButton.classList.add('bg-gradient-warning');
                            }

                            // Set the current button to blue
                            element.classList.remove('bg-gradient-warning');
                            element.classList.add('bg-primary');

                            // Update the last clicked button to the current one
                            lastClickedButton = element;
                            return;
                        }
                    </script>
                    <style>
                        .bg-gradient-warning {
                            background-color: yellow;
                            /* Original color */

                            /* Ensure it behaves like a button */
                        }

                        .bg-primary {
                            background-color: blue !important;

                            /* Change text color for visibility */
                        }

                        .separator-line {
                            height: 1px;
                            /* Set the height */
                            background-color: rgb(0, 0, 0);
                            /* Set the color */
                            border: none;
                            /* Remove any borders */
                        }
                    </style>

                    <%@ include file="component/footer.jsp" %>
                        <%@ include file="component/script.jsp" %>
                            <script src="<%=assetspath%>js/main.js"></script>
                            <script src="<%=assetspath%>js/chartjs.min.js"></script>
                            <script>

                                function updatebatch(url) {
                                    var xhttp = new XMLHttpRequest();
                                    xhttp.open("GET", url, true);

                                    xhttp.onreadystatechange = function () {
                                        if (this.readyState === 4) {
                                            if (this.status === 200) {
                                                var updatedBatch = JSON.parse(this.responseText);
                                                loadBatch();
                                            } else {
                                                console.log("Error: " + this.status); // Log any error
                                            }

                                        }
                                    };

                                    xhttp.send();
                                }
                                window.onload = function () {
                                    loadBatch(null);
                                    viewloadBatch();
                                }

                                function countActiveBatchStudent(batchId, callback) {
                                    console.log(batchId);
                                    $.ajax({
                                        url: 'http://localhost:9090/admin/getAllStudentByBatchId?batchId=' + batchId,
                                        type: 'GET',
                                        dataType: 'json',
                                        success: function (response) {
                                            const count = response.length;
                                            console.log('Count of students:', count);
                                            callback(count); // Call the callback with the count
                                        },
                                        error: function (error) {
                                            console.error('Error:', error);
                                            callback("not found"); // Call the callback with an error message
                                        }
                                    });
                                }

                                

                                function viewloadBatch() {
                                    $.ajax({
                                        url: 'http://localhost:9090/admin/findActivebatches',
                                        type: 'GET',
                                        dataType: 'json',
                                        success: function (response) {
                                            $('#batchesContainer').empty();

                                            if (Array.isArray(response) && response.length > 0) {
                                                response.forEach(batch => {
                                                    const cid=batch.batchId;
                                                    // Call countActiveBatchStudent with a callback
                                                    countActiveBatchStudent(cid,function (count) {
                                                        // Construct the card HTML inside the callback
                                                        const cardHtml = `
                            <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
                                <div class="card">
                                    <div class="card-body p-3">
                                        <div class="row">
                                            <div class="col-8">
                                                <div class="numbers">
                                                    <p class="text-sm mb-0 font-weight-bold">`+ batch.batchTopic + `</p>
                                                    <h5 class="font-weight-bolder">`+ count + `</h5>
                                                    <p class="mb-0">
                                                        <span class="text-success text-sm font-weight-bolder">100%</span>
                                                        since Today
                                                        <a class="bg-gradient-warning btn text-light mt-1"  
                                                           onclick="batchDetails(`+ batch.batchId + `); changeColor(this);">View</a>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="col-4 text-end">
                                                <div class="icon icon-shape bg-gradient-warning shadow-warning text-center rounded-circle">
                                                    <i class="fa-solid fa-list-check text-light text-lg opacity-10" aria-hidden="true"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `;

                                                        // Append the card to the container
                                                        $('#batchesContainer').append(cardHtml);
                                                    });
                                                });
                                            } else {
                                                $('#batchesContainer').append('<div class="footer-newsletter"><div class="container"><div class="row justify-content-center text-center"><div class="col-lg-6"><h1>No  Available</h1></div></div></div></div>');
                                            }
                                        },
                                        error: function (error) {
                                            console.error('Error:', error);
                                            $('#batchesContainer').append('<p>Error loading batches. Please try again.</p>');
                                        }
                                    });
                                }

                                function loadBatch(element) {
                                    changeColor(element);

                                    $('#backtable').empty();
                                    $.ajax({
                                        url: 'http://localhost:9090/admin/getBatch',
                                        type: 'GET',
                                        dataType: 'json', // No need to parse the response manually
                                        success: function (response) {
                                            // Clear the existing table body to prevent duplicates
                                            $('#tableid').empty();
                                            // Iterate over the response and append rows
                                            response.forEach(batch => {
                                                const newRow = $('<tr>').attr('id', batch.batchid);
                                                newRow.append(
                                                    batch.currentStatus === "Completed" ? $('<td>').html('<p class="text-xs font-weight-bold mb-0">Name:</p><h6 class="text-sm mb-0">' + batch.batchTopic + '</h6>') : null,
                                                    batch.currentStatus === "Completed" ? $('<td>').html('<p class="text-xs font-weight-bold mb-0">batch status </p><h6 class="text-sm mb-0">' + batch.currentStatus + '</h6>') : null,
                                                    batch.currentStatus === "Completed" ? $('<td>').html('<p class="text-xs font-weight-bold mb-0">batchId</p><h6 class="text-sm mb-0">' + batch.batchId + '</h6>') : null,
                                                    batch.currentStatus === "Completed" ? $('<td>').html('<p class="text-xs font-weight-bold mb-0">start date  </p><h6 class="text-sm mb-0">' + batch.startDate + '</h6>') : null,
                                                    batch.currentStatus === "Completed" ? $('<td>').html('<p class="text-xs font-weight-bold mb-0">end date  </p><h6 class="text-sm mb-0">' + batch.endDate + '</h6>') : null,
                                                    batch.currentStatus === "Completed" ? $('<td>').html('<a class="btn btn-outline-info"  onclick="batchDetails(\'' + batch.batchId + '\')">Details</a>') : null
                                                );
                                                // Append the new row to the table
                                                $('#tableid').append(newRow);
                                                // const separatorRow = $('<tr>').append(
                                                //     $('<td colspan="4" class="separator-line"/>') // Use a CSS class for styling
                                                // );
                                                // $('#tableid').append(separatorRow);
                                            });
                                        },
                                        error: function (error) {
                                            console.error('Error:', error);
                                        }
                                    });

                                }

                                function batchDetails(batchId) {
                                    console.log("batchid=>" + batchId);
                                    $('#backtable').empty();
                                    // Create a new row
                                    const backRow = $('<tr>');
                                    // Append the Back button to the row
                                    backRow.append(
                                        $('<td>').attr('colspan', 2).html('<button type="button" class="btn btn-outline-secondary" style="width: 100%;" onclick="loadBatch(this)">Back all batches</button>')
                                    );
                                    // Append the new row to the table
                                    $('#backtable').append(backRow);
                                    $.ajax({
                                        url: 'http://localhost:9090/admin/getAllStudentByBatchId?batchId=' + batchId,
                                        type: 'GET',
                                        dataType: 'json', // No need to parse the response manually
                                        success: function (response) {
                                            // Clear the existing table body to prevent duplicates
                                            $('#tableid').empty();
                                            var check = true;
                                            console.log(response);
                                            // Iterate over the response and append rows
                                            response.forEach(batch => {
                                                check = false;
                                                const newRow = $('<tr>').attr('id', batch.batchid);
                                                newRow.append(
                                                    $('<td>').html('<p class="text-xs font-weight-bold mb-0">rollNo:</p><h6 class="text-sm mb-0">' + batch.rollNo + '</h6>'),
                                                    $('<td>').html('<p class="text-xs font-weight-bold mb-0">Name:</p><h6 class="text-sm mb-0">' + batch.user.name + '</h6>'),
                                                    $('<td>').html('<p class="text-xs font-weight-bold mb-0">Id:</p><h6 class="text-sm mb-0">' + batch.user.id + '</h6>'),
                                                    $('<td>').html('<p class="text-xs font-weight-bold mb-0">ContactNo:</p><h6 class="text-sm mb-0">' + batch.user.contactNo + '</h6>'),
                                                    $('<td>').html('<p class="text-xs font-weight-bold mb-0">attendanceCount Present:</p><h6 class="text-sm mb-0">' + batch.attendanceCount + '</h6>'),
                                                    $('<td>').html('<a class="btn btn-outline-info" href="#' + batch.batchid + '">Details</a>')
                                                );
                                                // Append the new row to the table
                                                $('#tableid').append(newRow);

                                            });
                                            if (check) {
                                                const newRow = $('<tr>');
                                                $('#tableid').empty();
                                                newRow.append(
                                                    $('<td>').html(' <h2 class="text-sm mb-0 "> not availeble student ..  </h2>'));
                                                $('#tableid').append(newRow);
                                            }
                                        },
                                        error: function (error) {
                                            console.error('Error:', error);
                                            const newRow = $('<tr>');
                                            $('#tableid').empty();
                                            newRow.append(
                                                $('<td>').html(' <h6 class="text-sm mb-0 "> page not found  Error : 404 </h6>'));
                                            $('#tableid').append(newRow);
                                        }
                                    });

                                }


                            </script>
            </body>

            </html>