CREATE VIEW [Protrans].[dbo].[dokument_v]
AS
select doc.document_id, p.project_number , p.name as Projectname, D.name as Type, doc.header, a.registered
from SuperofficeCRM7.crm7.PROJECT p inner join
        SuperofficeCRM7.crm7.APPOINTMENT a on a.project_id = p.project_id inner join
       SuperofficeCRM7.crm7.DOCTMPL D on D.DocTmpl_id = a.task_idx inner join
       SuperofficeCRM7.crm7.DOCument doc on doc.document_id = a.document_id
where D.DOCTMPL_ID IN (451,452,453,454,455,354,165,166,352,331)