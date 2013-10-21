update customer_order set status=replace(status,'$Stein','$Takstein')
where sent is null

/********************************************************************************/

update customer_order set status=	replace(
				replace(
				replace(status,'$Takstein;VX','')
				,'$Takstein;V','')
				,'$Takstein;','')
where sent is null
