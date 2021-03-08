/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import org.hibernate.dialect.SQLServerDialect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtransSQLServerDialect extends SQLServerDialect {
/*    */    public ProtransSQLServerDialect() {
/* 11 */       this.registerColumnType(12, "nvarchar($l)");
/* 12 */    }
/*    */ }
