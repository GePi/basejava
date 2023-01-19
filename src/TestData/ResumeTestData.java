package TestData;

import model.*;
import utils.DateUtils;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = createResume("uuid1", "Федор Михайлович Достоевский");

        System.out.println(r.getFullName());
        System.out.println("=".repeat(40));

        r.getContacts().forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("=".repeat(40));

        r.getSections().forEach((k, v) -> {
            System.out.println(k.getTitle() + ":");
            System.out.println(v.toString());
            System.out.println("=".repeat(40));
        });
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume r = new Resume(uuid, fullName);
        fillContacts(r);
/*
        fillTextSection(r);
        fillListSection(r);
        fillOrganizationSection(r);
*/
        return r;
    }

    private static void fillContacts(Resume r) {
        r.addContact(ContactType.MOBILE_PHONE_NUMBER, "+7(921) 855-0482");
        r.addContact(ContactType.SKYPE_LOGIN, "skype:grigory.kislin");
        r.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        r.addContact(ContactType.LINKEDIN_PROFILE, "https://www.linkedin.com/in/gkislin");
        r.addContact(ContactType.GITHUB_PROFILE, "https://github.com/gkislin");
        r.addContact(ContactType.STACKOVERFLOW_PROFILE, "https://stackoverflow.com/users/548473");
        r.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
    }

    private static void fillTextSection(Resume r) {
        r.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
    }

    private static void fillListSection(Resume r) {
        r.addSection(SectionType.ACHIEVEMENT,
                new ListSection()
                        .addLineByLine("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет")
                        .addLineByLine("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.")
                        .addLineByLine("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.")
                        .addLineByLine("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.")
                        .addLineByLine("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.")
                        .addLineByLine("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).")
                        .addLineByLine("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        r.addSection(SectionType.QUALIFICATION, new ListSection()
                .addLineByLine("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2")
                .addLineByLine("Version control: Subversion, Git, Mercury, ClearCase, Perforce")
                .addLineByLine("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB")
                .addLineByLine("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy")
                .addLineByLine("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts")
                .addLineByLine("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).")
                .addLineByLine("Python: Django.")
                .addLineByLine("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js")
                .addLineByLine("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka")
                .addLineByLine("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.")
                .addLineByLine("Инструменты: Maven + plugin development, Gradle, настройка Ngnix")
                .addLineByLine("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer")
                .addLineByLine("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования")
                .addLineByLine("Родной русский, английский \"upper intermediate\""));
    }

    public static void fillOrganizationSection(Resume r) {
        fillWorkSection(r);
        fillEducationSection(r);
    }

    private static void fillWorkSection(Resume r) {
        OrganizationSection workSection = new OrganizationSection();
        r.addSection(SectionType.EXPERIENCE, workSection);
        workSection.addItems(
                new Organization(
                        "Java Online Projects",
                        "http://javaops.ru/",
                        new Organization.Period(
                                DateUtils.of(2015, 10),
                                "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов, стажировок.")),
                new Organization("" +
                        "Wrike",
                        "https://www.wrike.com/",
                        new Organization.Period(
                                DateUtils.of(2014, 10),
                                DateUtils.lastDayOf(2016, 1),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")),
                new Organization(
                        "RIT Center",
                        "https://www.wrike.com/",
                        new Organization.Period(
                                DateUtils.of(2012, 4),
                                DateUtils.lastDayOf(2014, 10),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")),
                new Organization(
                        "Luxoft (Deutsche Bank)",
                        "http://www.luxoft.ru/",
                        new Organization.Period(
                                DateUtils.of(2010, 12),
                                DateUtils.lastDayOf(2012, 4),
                                "Ведущий программист",
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")),
                new Organization(
                        "Yota",
                        "https://www.yota.ru/",
                        new Organization.Period(
                                DateUtils.of(2008, 6),
                                DateUtils.lastDayOf(2010, 12),
                                "Ведущий специалист",
                                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")),
                new Organization(
                        "Enkata",
                        "http://enkata.com/",
                        new Organization.Period(
                                DateUtils.of(2007, 3),
                                DateUtils.lastDayOf(2008, 6),
                                "Разработчик ПО",
                                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")),
                new Organization(
                        "Siemens AG",
                        "https://www.siemens.com/ru/ru/home.html",
                        new Organization.Period(
                                DateUtils.of(2005, 1),
                                DateUtils.lastDayOf(2007, 2),
                                "Разработчик ПО",
                                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")),
                new Organization(
                        "Alcatel",
                        "http://www.alcatel.ru/",
                        new Organization.Period(
                                DateUtils.of(1997, 9),
                                DateUtils.lastDayOf(2005, 1),
                                "Инженер по аппаратному и программному тестированию",
                                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")));
    }

    private static void fillEducationSection(Resume r) {
        OrganizationSection studySection = new OrganizationSection();
        r.addSection(SectionType.EDUCATION, studySection);
        studySection.addItems(
                new Organization(
                        "Coursera",
                        "https://www.coursera.org/course/progfun",
                        new Organization.Period(
                                DateUtils.of(2013, 3),
                                DateUtils.lastDayOf(2013, 5),
                                "'Functional Programming Principles in Scala' by Martin Odersky")),
                new Organization(
                        "Luxoft",
                        "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        new Organization.Period(
                                DateUtils.of(2011, 3),
                                DateUtils.lastDayOf(2011, 4),
                                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'")),
                new Organization(
                        "Siemens AG",
                        "http://www.siemens.ru/",
                        new Organization.Period(
                                DateUtils.of(2005, 1),
                                DateUtils.lastDayOf(2005, 4),
                                "3 месяца обучения мобильным IN сетям (Берлин)")),
                new Organization(
                        "Alcatel",
                        "http://www.alcatel.ru/",
                        new Organization.Period(
                                DateUtils.of(1997, 9),
                                DateUtils.lastDayOf(1998, 3),
                                "6 месяцев обучения цифровым телефонным сетям (Москва)")),
                new Organization(
                        "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                        "http://www.ifmo.ru/",
                        new Organization.Period(
                                DateUtils.of(1993, 9),
                                DateUtils.lastDayOf(1996, 7),
                                "Аспирантура (программист С, С++)"),
                        new Organization.Period(
                                DateUtils.of(1987, 9),
                                DateUtils.lastDayOf(1993, 7),
                                "Инженер (программист Fortran, C)")),
                new Organization(
                        "Заочная физико-техническая школа при МФТИ",
                        "http://www.school.mipt.ru/",
                        new Organization.Period(
                                DateUtils.of(1984, 9),
                                DateUtils.lastDayOf(1987, 6),
                                "Закончил с отличием")));
    }
}
