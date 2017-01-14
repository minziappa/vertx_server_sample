<#import "../layout/realTimeLayout.ftl" as layout>
<@layout.myLayout>

			<!-- start #content -->
				<div id="content">
					<fieldset id="fieldset1">
					<legend>RealTime</legend>

					<table class="listing">
						<thead>
							<tr id="charge_hearder">
							</tr>
						</thead>
						<tbody>
							<tr id="charge_body">
							</tr>
						</tbody>
					</table>
					<table class="listing">
						<thead>
							<tr id="charge_hearder1">
							</tr>
						</thead>
						<tbody>
							<tr id="charge_body1">
							</tr>
						</tbody>
					</table>

					<table class="listing">
						<thead>
							<tr>
								<th>aaa</th>
								<th>bbb</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th id="nowSum" style="color:red;">reading....</th>
								<th id="totalSum" style="color:red;">reading....</th>
							</tr>
						</tbody>
					</table>
					<div id="container" style="height: 500px; min-width: 500px; max-width: 1000px;"></div>
					<div style="clear: both;">&nbsp;</div>
					</fieldset>
				</div>
			<!-- end #content -->

</@layout.myLayout>