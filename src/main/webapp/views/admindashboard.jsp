<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="component/head.jsp"%>
<link href="<%=assetspath%>css/demo.css"
	rel="stylesheet">
</head>
<body>
	<%@ include file="component/navbar.jsp"%>
	<section class="col py-4">
		<div class="row">
			<div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
				<div class="card ">
					<div class=" card-body p-3">
						<div class="row">
							<div class="col-8">
								<div class="numbers">
									<p class="text-sm mb-0 text-uppercase font-weight-bold">Attendance</p>
									<h5 class="font-weight-bolder">10</h5>
									<p class="mb-0">
										<span class="text-success text-sm font-weight-bolder">100%</span>
										since Today
									</p>
								</div>
							</div>
							<div class="col-4 text-end">
								<div
									class="icon icon-shape bg-gradient-primary shadow-primary text-center rounded-circle">
									<i class="fa-solid fa-calendar-days text-lg opacity-10"
										aria-hidden="true"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
				<div class="card">
					<div class="card-body p-3">
						<div class="row">
							<div class="col-8">
								<div class="numbers">
									<p class="text-sm mb-0  font-weight-bold">No. of Test</p>
									<h5 class="font-weight-bolder">4</h5>
									<p class="mb-0">
										<span class="text-success text-sm font-weight-bolder">100%</span>
										since Today
									</p>
								</div>
							</div>
							<div class="col-4 text-end">
								<div
									class="icon icon-shape bg-gradient-warning shadow-warning text-center rounded-circle">
									<i class="fa-solid fa-list-check text-light text-lg opacity-10"
										aria-hidden="true"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
				<div class="card">
					<div class="card-body p-3">
						<div class="row">
							<div class="col-8">
								<div class="numbers">
									<p class="text-sm mb-0 text-uppercase font-weight-bold">Progress</p>
									<h5 class="font-weight-bolder">20%</h5>
									<p class="mb-0">
										<span class="text-success text-sm font-weight-bolder">100%</span>
										since Today
									</p>
								</div>
							</div>
							<div class="col-4 text-end">
								<div
									class="icon icon-shape bg-gradient-success shadow-primary text-center rounded-circle">
									<i class="fa-solid fa-chart-pie text-lg opacity-10"
										aria-hidden="true"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
				<div class="card ">
					<div class="card-body p-3">
						<div class="row">
							<div class="col-8">
								<div class="numbers">
									<p class="text-sm mb-0 text-uppercase font-weight-bold">Average</p>
									<h5 class="font-weight-bolder">75.6</h5>
									<p class="mb-0">
										<span class="text-success text-sm font-weight-bolder">100%</span>
										since Today
									</p>
								</div>
							</div>
							<div class="col-4 text-end">
								<div
									class="icon icon-shape bg-gradient-danger shadow-primary text-center rounded-circle">
									<i class="fa-solid fa-square-poll-vertical text-lg opacity-10"
										aria-hidden="true"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-4">
			<div class="col-lg-7 mb-lg-0 mb-4">
				<div class="card ">
					<div class="card-header pb-0 p-3">
						<div class="d-flex justify-content-between">
							<h6 class="mb-2">Students</h6>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table align-items-center ">
							<tbody>
								<tr>
									<td>
										<div class="text-center">
											<p class="text-xs font-weight-bold mb-0">Name:</p>
											<h6 class="text-sm mb-0">Amit Yadav</h6>
										</div>
									</td>
									<td>
										<div class="text-center">
											<p class="text-xs font-weight-bold mb-0">Email:</p>
											<h6 class="text-sm mb-0">yamit4757@gmail.com</h6>
										</div>
									</td>
									<td class="align-middle text-sm">
										<div class="col text-center">
											<p class="text-xs font-weight-bold mb-0">Phone No:</p>
											<h6 class="text-sm mb-0">29.9%</h6>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="col-lg-5">
				<div class="card card-carousel overflow-hidden h-100 p-0">
					<div class="card z-index-2 h-100">
						<div class="card-header pb-0 pt-3 bg-transparent">
							<h6 class="text-capitalize">Notice</h6>
						</div>
						<div class="card-body p-3"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="component/footer.jsp"%>
	<%@ include file="component/script.jsp"%>
</body>
</html>
