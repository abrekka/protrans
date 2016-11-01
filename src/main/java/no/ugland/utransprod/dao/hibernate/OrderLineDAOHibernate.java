package no.ugland.utransprod.dao.hibernate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.OrderLineDAO;
import no.ugland.utransprod.dao.OrdlnDAO;
import no.ugland.utransprod.gui.model.OrderLineAttributeCriteria;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for ORDER_LINE for hibernate
 * @author atle.brekka
 */
public class OrderLineDAOHibernate extends BaseDAOHibernate<OrderLine> implements OrderLineDAO {
    private OrdlnDAO ordlnDAO;

    /**
     * 
     */
    public OrderLineDAOHibernate() {
        super(OrderLine.class);
    }

    public final void setOrdlnDAO(final OrdlnDAO aDAO) {
        ordlnDAO = aDAO;
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findUnproducedByArticle(no.ugland.utransprod.model.ArticleType)
     */
    public List<OrderLine> findUnproducedByArticle(final ArticleType article) {
        return findUnproducedByArticle(article, null);
    }

    /**
     * Finner uproduserte ordrelinjer basert på artikkel
     * @param article
     * @param orderNr
     * @return ordrelinjer
     */
    @SuppressWarnings("unchecked")
    private List<OrderLine> findUnproducedByArticle(final ArticleType article, final String orderNr) {
        return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                // ordrelinjer med ordre og garasjeartikkel
                StringBuffer sqlStringConstructionType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,ConstructionTypeArticle constructionTypeArticle"
                                + " left outer join customerOrder.transport where ")
                        .append("orderLine.order = customerOrder and ");
                if (orderNr != null) {
                    sqlStringConstructionType.append("customerOrder.orderNr='").append(orderNr).append(
                            "' and ");
                }
                sqlStringConstructionType.append("customerOrder.sent is null and ").append(
                        "orderLine.constructionTypeArticle = constructionTypeArticle and ").append(
                        "constructionTypeArticle.articleType=:articleType ");

                // ordrelinjer med artikler direkte
                StringBuffer sqlStringArticleType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder"
                                + " left outer join customerOrder.transport where ")
                        .append("orderLine.order = customerOrder and ");

                if (orderNr != null) {
                    sqlStringArticleType.append("customerOrder.orderNr='").append(orderNr).append("' and ");
                }

                sqlStringArticleType.append("customerOrder.sent is null and ").append(
                        "orderLine.articleType=:articleType ");

                List<OrderLine> ordersConstructionTypeList = session.createQuery(
                        sqlStringConstructionType.toString()).setParameter("articleType", article).list();

                List<OrderLine> ordersArticleTypeList = session.createQuery(sqlStringArticleType.toString())
                        .setParameter("articleType", article).list();

                ordersConstructionTypeList.addAll(ordersArticleTypeList);
                Collections.sort(ordersConstructionTypeList, new OrderLineComparator());
                return ordersConstructionTypeList;
            }

        });
    }

    /**
     * Finner uproduserte basert på artikkeltype og kundenummer
     * @param article
     * @param customerNr
     * @return ordrelinjer
     */
    @SuppressWarnings("unchecked")
    private List<OrderLine> findUnproducedByCustomerNrArticle(final ArticleType article,
            final Integer customerNr) {
        return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                // ordrelinjer med ordre og garasjeartikkel
                StringBuffer sqlStringConstructionType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,Customer customer,ConstructionTypeArticle constructionTypeArticle,Transport transport where ")
                        .append("orderLine.order = customerOrder and ").append(
                                "customerOrder.customer=customer and ").append("customer.customerNr=")
                        .append(customerNr).append(" and ")
                        .append("customerOrder.transport = transport and ").append(
                                "customerOrder.sent is null and ").append(
                                "orderLine.constructionTypeArticle = constructionTypeArticle and ").append(
                                "constructionTypeArticle.articleType=:articleType and ").append(
                                "transport.transportName <> 'Historisk' ");

                // ordrelinjer med artikler direkte
                StringBuffer sqlStringArticleType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,Customer customer,Transport transport where ")
                        .append("orderLine.order = customerOrder and ")

                        .append("customerOrder.customer=customer and ").append("customer.customerNr=")
                        .append(customerNr).append(" and ")
                        .append("customerOrder.transport = transport and ").append(
                                "customerOrder.sent is null and ").append(
                                "orderLine.articleType=:articleType and ").append(
                                "transport.transportName <> 'Historisk'");

                List<OrderLine> ordersConstructionTypeList = session.createQuery(
                        sqlStringConstructionType.toString()).setParameter("articleType", article).list();

                List<OrderLine> ordersArticleTypeList = session.createQuery(sqlStringArticleType.toString())
                        .setParameter("articleType", article).list();

                ordersConstructionTypeList.addAll(ordersArticleTypeList);
                Collections.sort(ordersConstructionTypeList, new OrderLineComparator());
                return ordersConstructionTypeList;
            }

        });
    }

    /**
     * Klasse som sammenlikner ordrelinjer
     * @author atle.brekka
     */
    static class OrderLineComparator implements Comparator<OrderLine>, Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * @param o1
         * @param o2
         * @return -1,0 eller 1
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(OrderLine o1, OrderLine o2) {

            if (o1.getOrder().getTransport() == null && o2.getOrder().getTransport() == null) {
                return 0;
            } else if (o1.getOrder().getTransport() == null) {
                return 1;
            } else if (o2.getOrder().getTransport() == null) {
                return -1;
            }
            Transport transport1 = o1.getOrder().getTransport();
            Transport transport2 = o2.getOrder().getTransport();

            return new CompareToBuilder().append(transport1.getLoadingDate(), transport2.getLoadingDate())
                    .append(transport1.getLoadTime(), transport2.getLoadTime()).append(
                            transport1.getTransportName(), transport2.getTransportName()).toComparison();

        }

    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#lazyLoad(no.ugland.utransprod.model.OrderLine,
     *      no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum[])
     */
    public void lazyLoad(final OrderLine orderLine, final LazyLoadOrderLineEnum[] enums) {
        if (orderLine != null && orderLine.getOrderLineId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                @SuppressWarnings("incomplete-switch")
                public Object doInHibernate(Session session) throws HibernateException {
                    if (!session.contains(orderLine)) {
                        session.load(orderLine, orderLine.getOrderLineId());
                    }
                    Set<?> set;
                    List<LazyLoadOrderLineEnum> enumList = Arrays.asList(enums);

                    for (LazyLoadOrderLineEnum lazyEnum : enums) {
                        switch (lazyEnum) {
                        case ORDER_LINE_ATTRIBUTE:
                            set = orderLine.getOrderLineAttributes();
                            if (!Hibernate.isInitialized(set)) {
                                set.size();
                            }
                            break;
                        case ORDER_LINES:
                            Set<OrderLine> orderLines = orderLine.getOrderLines();
                            if (!Hibernate.isInitialized(orderLines)) {
                                orderLines.size();
                            }
                            if (enumList.contains(LazyLoadOrderLineEnum.ORDER_LINES_ORDER_LINES)) {
                                for (OrderLine line : orderLines) {
                                    lazyLoad(line, new LazyLoadOrderLineEnum[] {
                                            LazyLoadOrderLineEnum.ORDER_LINES,
                                            LazyLoadOrderLineEnum.ORDER_LINES_ORDER_LINES,
                                            LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
                                }
                            }
                        }
                    }
                    return null;
                }

            });

        }

    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#countByDate(no.ugland.utransprod.dao.hibernate.QuerySettings)
     */
    public Integer countByDate(final QuerySettings querySettings) {
        return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                StringBuffer queryString = new StringBuffer();

                switch (querySettings.getDateEnum()) {
                case INVOICE_DATE:
                    queryString
                            .append("select count(*) from Order where invoiceDate between :fromDate and :toDate");
                    break;
                case ORDER_DATE:
                    queryString
                            .append("select count(*) from Order where orderDate between :fromDate and :toDate");
                    break;
                case TRANSPORT_WEEK:
                    queryString
                            .append(
                                    "select count(customerOrder) from Order customerOrder,Transport transport where ")
                            .append("customerOrder.transport=transport and ");
                    queryString
                            .append("cast(transport.transportYear as string)||")
                            .append(
                                    "cast(transport.transportWeek+10 as string) between :transportFrom and :transportTo");
                    break;
                }

                final Query query = session.createQuery(queryString.toString());
                switch (querySettings.getDateEnum()) {
                case INVOICE_DATE:
                case ORDER_DATE:
                    query.setParameter("fromDate", querySettings.getDateFrom()).setParameter("toDate",
                            querySettings.getDateTo());
                    break;
                case TRANSPORT_WEEK:
                    query.setParameter("transportFrom", querySettings.getTransportFrom()).setParameter(
                            "transportTo", querySettings.getTransportTo());
                    break;
                }

                return query.iterate().next();
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findByConstructionTypeArticleAttributes(java.util.List,
     *      no.ugland.utransprod.dao.hibernate.QuerySettings)
     */
    @SuppressWarnings("unchecked")
    public List<Order> findByConstructionTypeArticleAttributes(final List<OrderLine> criterias,
            final QuerySettings querySettings) {
        return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                StringBuffer buffer = new StringBuffer("select customerOrder from Order customerOrder where ");

                switch (querySettings.getDateEnum()) {
                case INVOICE_DATE:
                    buffer.append("customerOrder.invoiceDate between :fromDate and :toDate ");
                    break;
                case ORDER_DATE:
                    buffer.append("customerOrder.orderDate between :fromDate and :toDate ");
                    break;
                case TRANSPORT_WEEK:
                    buffer.append(" (exists(select 1 from Transport transport where ").append(
                            "customerOrder.transport = transport and ");

                    buffer
                            .append("cast(transport.transportYear as string)||")
                            .append(
                                    "cast(transport.transportWeek+10 as string) between :transportFrom and :transportTo))");

                    break;
                }

                Integer counter = 1;
                Set<OrderLineAttribute> orderLineAttributes;
                OrderLineAttributeCriteria attCriteria;
                OperandEnum operandEnum = querySettings.getOperandEnum();
                for (OrderLine orderLine : criterias) {

                    orderLineAttributes = orderLine.getOrderLineAttributes();
                    for (OrderLineAttribute attribute : orderLineAttributes) {
                        if (counter == 1) {
                            operandEnum = OperandEnum.AND;

                        } else {
                            operandEnum = querySettings.getOperandEnum();

                        }

                        buffer.append(operandEnum.getOperandString());
                        if (counter == 1) {
                            buffer.append("(");
                        }

                        attCriteria = (OrderLineAttributeCriteria) attribute;

                        if (attCriteria.isYesNo()) {
                            buffer = buildYesNoSqlArticle(buffer, attCriteria, orderLine, operandEnum);
                        } else if (attCriteria.getChoices() != null && attCriteria.getChoices().size() != 0) {
                            buffer = buildLikeSqlArticle(buffer, attCriteria, orderLine, operandEnum);
                        } else {
                            if (attCriteria.getAttributeValueFrom() != null
                                    && attCriteria.getAttributeValueFrom().length() != 0) {
                                if (attCriteria.getAttributeValueTo() != null
                                        && attCriteria.getAttributeValueTo().length() != 0) {
                                    buffer = buildBewteenSqlArticle(buffer, attCriteria, orderLine,
                                            operandEnum);
                                } else {
                                    buffer = buildEqGtSqlArticle(buffer, attCriteria, orderLine, operandEnum);
                                }

                            }
                        }
                        counter++;
                    }

                }
                buffer.append(")");
                Query query = session.createQuery(buffer.toString());
                switch (querySettings.getDateEnum()) {
                case INVOICE_DATE:
                case ORDER_DATE:
                    query.setParameter("fromDate", querySettings.getDateFrom()).setParameter("toDate",
                            querySettings.getDateTo());
                    break;
                case TRANSPORT_WEEK:
                    query.setParameter("transportFrom", querySettings.getTransportFrom()).setParameter(
                            "transportTo", querySettings.getTransportTo());
                    break;
                }
                return query.list();

            }

        });

    }

    /**
     * Lager felles sql for alle typer uthenting
     * @param buffer
     * @param attribute
     * @param orderLine
     * @param operand
     * @return sql
     */
    private StringBuffer buildCommonSql(StringBuffer buffer, OrderLineAttributeCriteria attribute,
            OrderLine orderLine, OperandEnum operand) {
        StringBuffer returnBuffer = new StringBuffer(buffer);
        returnBuffer.append(
                "exists(select 1 from OrderLine orderLine,OrderLineAttribute orderLineAttribute where ")
                .append("orderLine.order = customerOrder and ").append("orderLine.articlePath = '").append(
                        orderLine.getGeneratedArticlePath()).append("' and ").append(
                        "orderLineAttribute.orderLine = orderLine and ").append(
                        "orderLineAttribute.orderLineAttributeName = '").append(attribute.getAttributeName())
                .append("' and ");
        return returnBuffer;
    }

    /**
     * Lager sql for uthenting av attributter med Ja/Nei valg
     * @param buffer
     * @param attribute
     * @param orderLine
     * @param operandEnum
     * @return sql
     */
    StringBuffer buildYesNoSqlArticle(StringBuffer buffer, OrderLineAttributeCriteria attribute,
            OrderLine orderLine, OperandEnum operandEnum) {
        buffer = buildCommonSql(buffer, attribute, orderLine, operandEnum);
        buffer.append("orderLineAttribute.orderLineAttributeValue = '").append(
                attribute.getAttributeValueFrom()).append("')");
        return buffer;
    }

    /**
     * Lager sql for attributter som skal være lik en verdi
     * @param buffer
     * @param attribute
     * @param orderLine
     * @param operandEnum
     * @return sql
     */
    StringBuffer buildLikeSqlArticle(StringBuffer buffer, OrderLineAttributeCriteria attribute,
            OrderLine orderLine, OperandEnum operandEnum) {
        buffer = buildCommonSql(buffer, attribute, orderLine, operandEnum);
        buffer.append("orderLineAttribute.orderLineAttributeValue like '").append(
                attribute.getAttributeValueFrom()).append("')");
        return buffer;
    }

    /**
     * Lager sql for attributt som skal være mellom to verdier
     * @param buffer
     * @param attribute
     * @param orderLine
     * @param operandEnum
     * @return sql
     */
    StringBuffer buildBewteenSqlArticle(StringBuffer buffer, OrderLineAttributeCriteria attribute,
            OrderLine orderLine, OperandEnum operandEnum) {
        buffer = buildCommonSql(buffer, attribute, orderLine, operandEnum);
        buffer.append("orderLineAttribute.orderLineAttributeValue between '").append(
                attribute.getAttributeValueFrom()).append("' and '").append(attribute.getAttributeValueTo())
                .append("')");
        return buffer;
    }

    /**
     * Lager sql for attributter som skal være lik eller større en en verdi
     * @param buffer
     * @param attribute
     * @param orderLine
     * @param operandEnum
     * @return sql
     */
    StringBuffer buildEqGtSqlArticle(StringBuffer buffer, OrderLineAttributeCriteria attribute,
            OrderLine orderLine, OperandEnum operandEnum) {
        buffer = buildCommonSql(buffer, attribute, orderLine, operandEnum);
        buffer.append("orderLineAttribute.orderLineAttributeValue >= '").append(
                attribute.getAttributeValueFrom()).append("')");
        return buffer;
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findUnpackageByArticle(no.ugland.utransprod.model.ArticleType)
     */
    public List<OrderLine> findUnpackageByArticle(final ArticleType article) {
        return findUnpackageByArticle(article, null);
    }

    /**
     * Finner upakkede ordrelinjer basert på artikkel
     * @param article
     * @param orderNr
     * @return ordrelinjer
     */
    @SuppressWarnings("unchecked")
    private List<OrderLine> findUnpackageByArticle(final ArticleType article, final String orderNr) {
        return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                // ordrelinjer med ordre og garasjeartikkel
                StringBuffer sqlStringConstructionType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,ConstructionTypeArticle constructionTypeArticle,Transport transport where ")
                        .append("orderLine.order = customerOrder and ");

                if (orderNr != null) {
                    sqlStringConstructionType.append("customerOrder.orderNr='").append(orderNr).append(
                            "' and ");
                }

                sqlStringConstructionType.append("customerOrder.transport = transport and ").append(
                        "customerOrder.sent is null and ").append(
                        "orderLine.constructionTypeArticle = constructionTypeArticle and ").append(
                        "constructionTypeArticle.articleType=:articleType and ").append(
                        "transport.transportName <> 'Historisk' ");

                // ordrelinjer med artikler direkte
                StringBuffer sqlStringArticleType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,Transport transport where ")
                        .append("orderLine.order = customerOrder and ");
                if (orderNr != null) {
                    sqlStringArticleType.append("customerOrder.orderNr='").append(orderNr).append("' and ");
                }
                sqlStringArticleType.append("customerOrder.transport = transport and ").append(
                        "customerOrder.sent is null and ").append("orderLine.articleType=:articleType and ")
                        .append("transport.transportName <> 'Historisk'");

                List<OrderLine> ordersConstructionTypeList = session.createQuery(
                        sqlStringConstructionType.toString()).setParameter("articleType", article).list();

                List<OrderLine> ordersArticleTypeList = session.createQuery(sqlStringArticleType.toString())
                        .setParameter("articleType", article).list();

                ordersConstructionTypeList.addAll(ordersArticleTypeList);
                Collections.sort(ordersConstructionTypeList, new OrderLineComparator());
                return ordersConstructionTypeList;
            }

        });

    }

    /**
     * Finner upakkede basert på artikkeltype og kundenummer
     * @param article
     * @param customerNr
     * @return ordrelinjer
     */
    @SuppressWarnings("unchecked")
    private List<OrderLine> findUnpackageByArticleCustomerNr(final ArticleType article,
            final Integer customerNr) {
        return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                // ordrelinjer med ordre og garasjeartikkel
                StringBuffer sqlStringConstructionType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,Customer customer,ConstructionTypeArticle constructionTypeArticle,Transport transport where ")
                        .append("orderLine.order = customerOrder and ").append(
                                "customerOrder.customer=customer and ").append("customer.customerNr=")
                        .append(customerNr).append(" and ")
                        .append("customerOrder.transport = transport and ").append(
                                "customerOrder.sent is null and ").append(
                                "orderLine.constructionTypeArticle = constructionTypeArticle and ").append(
                                "constructionTypeArticle.articleType=:articleType and ").append(
                                "transport.transportName <> 'Historisk' ");

                // ordrelinjer med artikler direkte
                StringBuffer sqlStringArticleType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,Customer customer,Transport transport where ")
                        .append("orderLine.order = customerOrder and ").append(
                                "customerOrder.customer=customer and ").append("customer.customerNr=")
                        .append(customerNr).append(" and ")
                        .append("customerOrder.transport = transport and ").append(
                                "customerOrder.sent is null and ").append(
                                "orderLine.articleType=:articleType and ").append(
                                "transport.transportName <> 'Historisk'");

                List<OrderLine> ordersConstructionTypeList = session.createQuery(
                        sqlStringConstructionType.toString()).setParameter("articleType", article).list();

                List<OrderLine> ordersArticleTypeList = session.createQuery(sqlStringArticleType.toString())
                        .setParameter("articleType", article).list();

                ordersConstructionTypeList.addAll(ordersArticleTypeList);
                Collections.sort(ordersConstructionTypeList, new OrderLineComparator());
                return ordersConstructionTypeList;
            }

        });

    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#lazyLoadTree(no.ugland.utransprod.model.OrderLine)
     */
    public void lazyLoadTree(final OrderLine orderLine) {
        if (orderLine != null && orderLine.getOrderLineId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException {

                    session.load(orderLine, orderLine.getOrderLineId());

                    Set<?> set;
                    set = orderLine.getOrderLineAttributes();
                    set.iterator();

                    Set<OrderLine> orderLines = orderLine.getOrderLines();
                    lazyLoadTreeOrderLines(orderLines);

                    return null;
                }
            });
        }

    }

    /**
     * Lazy laster ordrelinjetre
     * @param orderLines
     */
    void lazyLoadTreeOrderLines(Set<OrderLine> orderLines) {
        if (orderLines == null) {
            return;
        }
        Set<?> set;
        for (OrderLine orderLine : orderLines) {
            set = orderLine.getOrderLineAttributes();
            set.iterator();
            lazyLoadTreeOrderLines(orderLine.getOrderLines());
        }
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findUnproducedByOrderNrAndArticleName(java.lang.String,
     *      no.ugland.utransprod.model.ArticleType)
     */
    public List<OrderLine> findUnproducedByOrderNrAndArticleName(final String orderNr,
            final ArticleType articleType) {
        return findUnproducedByArticle(articleType, orderNr);
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findUnpackedByOrderNrAndArticleName(java.lang.String,
     *      no.ugland.utransprod.model.ArticleType)
     */
    public List<OrderLine> findUnpackedByOrderNrAndArticleName(final String orderNr,
            final ArticleType articleType) {
        return findUnpackageByArticle(articleType, orderNr);
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findUnproducedByCustomerNrAndArticleName(java.lang.Integer,
     *      no.ugland.utransprod.model.ArticleType)
     */
    public List<OrderLine> findUnproducedByCustomerNrAndArticleName(Integer customerNr,
            ArticleType articleType) {
        return findUnproducedByCustomerNrArticle(articleType, customerNr);
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findUnpackedByCustomerNrAndArticleName(java.lang.Integer,
     *      no.ugland.utransprod.model.ArticleType)
     */
    public List<OrderLine> findUnpackedByCustomerNrAndArticleName(Integer customerNr, ArticleType articleType) {
        return findUnpackageByArticleCustomerNr(articleType, customerNr);
    }

    /**
     * Finner basert på artikkeltype, attributt med gitt verdi og ordrenummer
     * @param articleType
     * @param attributeName
     * @param attributeValue
     * @param orderNr
     * @return ordrelinjer
     */
    @SuppressWarnings("unchecked")
    private List<OrderLine> findByArticleAndAttribute(final ArticleType articleType,
            final String attributeName, final String attributeValue, final String orderNr) {
        return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                // ordrelinjer med ordre og garasjeartikkel
                StringBuffer sqlStringConstructionType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,ConstructionTypeArticle constructionTypeArticle,"
                                + "OrderLineAttribute orderLineAttribute left outer join customerOrder.transport where ")
                        .append("orderLine.order = customerOrder and ");
                if (orderNr != null) {
                    sqlStringConstructionType.append("customerOrder.orderNr='").append(orderNr).append(
                            "' and ");
                }
                sqlStringConstructionType
                        .append("customerOrder.sent is null and ")
                        .append("orderLine.constructionTypeArticle = constructionTypeArticle and ")
                        .append("constructionTypeArticle.articleType=:articleType ")
                        .append(" and orderLineAttribute.orderLine=orderLine")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeName)=lower(:orderLineAttributeName)")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeValue)=lower(:orderLineAttributeValue)");

                // ordrelinjer med artikler direkte
                StringBuffer sqlStringArticleType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,"
                                + "OrderLineAttribute orderLineAttribute left outer join customerOrder.transport where ")
                        .append("orderLine.order = customerOrder and ");

                if (orderNr != null) {
                    sqlStringArticleType.append("customerOrder.orderNr='").append(orderNr).append("' and ");
                }

                sqlStringArticleType
                        .append("customerOrder.sent is null and ")
                        .append("orderLine.articleType=:articleType ")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeName)=lower(:orderLineAttributeName)")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeValue)=lower(:orderLineAttributeValue)")
                        .append(" and orderLineAttribute.orderLine=orderLine");

                List<OrderLine> ordersConstructionTypeList = session.createQuery(
                        sqlStringConstructionType.toString()).setParameter("articleType", articleType)
                        .setParameter("orderLineAttributeName", attributeName).setParameter(
                                "orderLineAttributeValue", attributeValue).list();

                List<OrderLine> ordersArticleTypeList = session.createQuery(sqlStringArticleType.toString())
                        .setParameter("articleType", articleType).setParameter("orderLineAttributeName",
                                attributeName).setParameter("orderLineAttributeValue", attributeValue).list();

                ordersConstructionTypeList.addAll(ordersArticleTypeList);
                Collections.sort(ordersConstructionTypeList, new OrderLineComparator());
                return ordersConstructionTypeList;
            }

        });

    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findByArticleAndAttribute(no.ugland.utransprod.model.ArticleType,
     *      java.lang.String, java.lang.String)
     */
    public List<OrderLine> findByArticleAndAttribute(ArticleType articleType, String attributeName,
            String attributeValue) {
        return findByArticleAndAttribute(articleType, attributeName, attributeValue, null);
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findByOrderNrArticleNameAndAttribute(java.lang.String,
     *      no.ugland.utransprod.model.ArticleType, java.lang.String,
     *      java.lang.String)
     */
    public List<OrderLine> findByOrderNrArticleNameAndAttribute(String orderNr, ArticleType articleType,
            String attributeName, String attributeValue) {
        return findByArticleAndAttribute(articleType, attributeName, attributeValue, orderNr);
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findByCustomerNrArticleNameAndAttribute(java.lang.Integer,
     *      no.ugland.utransprod.model.ArticleType, java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<OrderLine> findByCustomerNrArticleNameAndAttribute(final Integer customerNr,
            final ArticleType articleType, final String attributeName, final String attributeValue) {
        return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                // ordrelinjer med ordre og garasjeartikkel
                StringBuffer sqlStringConstructionType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,ConstructionTypeArticle constructionTypeArticle,Transport transport,OrderLineAttribute orderLineAttribute,Customer customer where ")
                        .append("orderLine.order = customerOrder and ");
                if (customerNr != null) {
                    sqlStringConstructionType.append("customerOrder.customer=customer and ").append(
                            "customer.customerNr=").append(customerNr).append(" and ");
                }
                sqlStringConstructionType
                        .append("customerOrder.transport = transport and ")
                        .append("customerOrder.sent is null and ")
                        .append("orderLine.constructionTypeArticle = constructionTypeArticle and ")
                        .append("constructionTypeArticle.articleType=:articleType and ")
                        .append("transport.transportName <> 'Historisk' ")
                        .append(" and orderLineAttribute.orderLine=orderLine")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeName)=lower(:orderLineAttributeName)")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeValue)=lower(:orderLineAttributeValue)");

                // ordrelinjer med artikler direkte
                StringBuffer sqlStringArticleType = new StringBuffer(
                        "select orderLine from OrderLine orderLine,Order customerOrder,Transport transport,OrderLineAttribute orderLineAttribute,Customer customer where ")
                        .append("orderLine.order = customerOrder and ");

                if (customerNr != null) {
                    sqlStringArticleType.append(" customerOrder.customer=customer and ").append(
                            "customerOrder.orderNr='").append(customerNr).append("' and ");
                }

                sqlStringArticleType
                        .append("customerOrder.transport = transport and ")
                        .append("customerOrder.sent is null and ")
                        .append("orderLine.articleType=:articleType and ")
                        .append("transport.transportName <> 'Historisk'")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeName)=lower(:orderLineAttributeName)")
                        .append(
                                " and lower(orderLineAttribute.orderLineAttributeValue)=lower(:orderLineAttributeValue)")
                        .append(" and orderLineAttribute.orderLine=orderLine");

                List<OrderLine> ordersConstructionTypeList = session.createQuery(
                        sqlStringConstructionType.toString()).setParameter("articleType", articleType)
                        .setParameter("orderLineAttributeName", attributeName).setParameter(
                                "orderLineAttributeValue", attributeValue).list();

                List<OrderLine> ordersArticleTypeList = session.createQuery(sqlStringArticleType.toString())
                        .setParameter("articleType", articleType).setParameter("orderLineAttributeName",
                                attributeName).setParameter("orderLineAttributeValue", attributeValue).list();

                ordersConstructionTypeList.addAll(ordersArticleTypeList);
                Collections.sort(ordersConstructionTypeList, new OrderLineComparator());
                return ordersConstructionTypeList;
            }

        });

    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findAllApplyable()
     */
    @SuppressWarnings("unchecked")
    public List<PackableListItem> findAllApplyable() {
        return (List<PackableListItem>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                return session.createCriteria(OrderLine.class).add(
                        Restrictions.ilike("articlePath", "Takstein")).createCriteria("order").add(
                        Restrictions.isNull("sent")).list();
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findApplyableByCustomerNr(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<PackableListItem> findApplyableByCustomerNr(final Integer customerNr) {
        return (List<PackableListItem>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                return session.createCriteria(OrderLine.class).add(
                        Restrictions.ilike("articlePath", "Takstein")).createCriteria("order").add(
                        Restrictions.isNull("sent")).createCriteria("customer").add(
                        Restrictions.eq("customerNr", customerNr)).list();
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#findApplyableByOrderNr(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<PackableListItem> findApplyableByOrderNr(final String orderNr) {
        return (List<PackableListItem>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {

                return session.createCriteria(OrderLine.class).add(
                        Restrictions.ilike("articlePath", "Takstein")).createCriteria("order").add(
                        Restrictions.eq("orderNr", orderNr)).list();
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.OrderLineDAO#refresh(no.ugland.utransprod.model.PackableListItem)
     */
    public void refresh(PackableListItem object) {
        getHibernateTemplate().load(OrderLine.class, object.getOrderLineId());

    }

    @SuppressWarnings("unchecked")
    public List<OrderLine> findAllConstructionTypeNotSent(final ProductArea productArea) {
        return (List<OrderLine>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {

                return session.createCriteria(OrderLine.class).add(
                        Restrictions.eq("articlePath", "Garasjetype")).setFetchMode("orderLineAttributes",
                        FetchMode.JOIN).createCriteria("order").add(Restrictions.isNull("sent")).add(
                        Restrictions.eq("productArea", productArea)).list();
            }

        });
    }

    public Ordln findOrdlnByOrderLine(Integer orderLineId) {
        if (orderLineId != null) {
            OrderLine orderLine = getObject(orderLineId);
            if (orderLine != null && orderLine.getOrdNo() != null) {
                return ordlnDAO.findByOrdNoAndLnNo(orderLine.getOrdNo(), orderLine.getLnNo());
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
	public List<Order> findTakstolOwnOrderByPeriode(final Periode periode) {
        return (List<Order>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                Date fromDate = Util.getFirstDateInWeek(periode.getYear(), periode.getWeek());
                Date toDate = Util.getLastDateInWeek(periode.getYear(), periode.getToWeek());
                String sql = "select customerOrder from Order customerOrder "
                        + "       where customerOrder.productArea.productArea<>:productArea and "
                        + "       exists (select 1 from OrderLine orderLine" +
                        		"           where orderLine.order=customerOrder and " +
                        		"                 orderLine.articlePath=:articlePath and "
                        + "                       orderLine.produced between :fromDate and :toDate and "
                        + "                       exists(select 1 from OrderLineAttribute attribute"
                        + "                                where attribute.orderLine=orderLine and "
                        + "                                      attribute.orderLineAttributeName=:attributeName and "
                        + "                                      attribute.orderLineAttributeValue<>:attributeValue))";
                return session.createQuery(sql).setParameter("productArea", "Takstol").setParameter(
                        "articlePath", Util.getTakstolArticleName()).setParameter("fromDate", fromDate)
                        .setParameter("toDate", toDate).setParameter("attributeName", "Egenordre")
                        .setParameter("attributeValue", "Nei").list();
            }
        });
    }

	public void fjernColli(final Integer orderLineId) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {

				String sql = "update OrderLine o set o.colli=null where o.orderLineId=:orderLineId";

				session.createQuery(sql).setInteger("orderLineId", orderLineId).executeUpdate();
				return null;
			}

		});
		
	}
}
