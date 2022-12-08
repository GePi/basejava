import model.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = new Resume("Борхес Хорхе Луис");
        fillContacts(r);
        fillPlainTextSection(r);
        fillWorkEducationExperienceSection(r);

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

    private static void fillContacts(Resume r) {
        r.addContact(Resume.ContactType.MOBILE_PHONE_NUMBER, "+7(921) 855-0482");
        r.addContact(Resume.ContactType.SKYPE_LOGIN, "skype:grigory.kislin");
        r.addContact(Resume.ContactType.EMAIL, "gkislin@yandex.ru");
        r.addContact(Resume.ContactType.LINKEDIN_PROFILE, "https://www.linkedin.com/in/gkislin");
        r.addContact(Resume.ContactType.GITHUB_PROFILE, "https://github.com/gkislin");
        r.addContact(Resume.ContactType.STACKOVERFLOW_PROFILE, "https://stackoverflow.com/users/548473");
        r.addContact(Resume.ContactType.HOMEPAGE, "http://gkislin.ru/");
    }

    private static void fillPlainTextSection(Resume r) {
        r.addSection(SectionType.OBJECTIVE, new PlainTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r.addSection(SectionType.PERSONAL, new PlainTextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        r.addSection(SectionType.ACHIEVEMENT, new PlainTextSection()
                .addLineByLine("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет")
                .addLineByLine("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.")
                .addLineByLine("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.")
                .addLineByLine("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.")
                .addLineByLine("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.")
                .addLineByLine("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).")
                .addLineByLine("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        r.addSection(SectionType.QUALIFICATION, new PlainTextSection()
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

    public static void fillWorkEducationExperienceSection(Resume r) {
        WorkStudyExperienceSection workSection = new WorkStudyExperienceSection();
        r.addSection(SectionType.EXPERIENCE, workSection);
        workSection.addItems(
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2015, 10, 1))
                        .setOrganizationName("Java Online Projects")
                        .setUrl("http://javaops.ru/")
                        .setWorkPosition("Автор проекта.")
                        .setEssenceOfWork("Создание, организация и проведение Java онлайн проектов, стажировок.")
                        .setCurrentPosition(true)
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2014, 10, 1))
                        .setTo(LocalDate.of(2016, 1, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Wrike")
                        .setUrl("https://www.wrike.com/")
                        .setWorkPosition("Старший разработчик (backend)")
                        .setEssenceOfWork("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2012, 4, 1))
                        .setTo(LocalDate.of(2014, 10, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("RIT Center")
                        .setUrl("https://www.wrike.com/")
                        .setWorkPosition("Java архитектор")
                        .setEssenceOfWork("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2010, 12, 1))
                        .setTo(LocalDate.of(2012, 4, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Luxoft (Deutsche Bank)")
                        .setUrl("http://www.luxoft.ru/")
                        .setWorkPosition("Ведущий программист")
                        .setEssenceOfWork("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2008, 6, 1))
                        .setTo(LocalDate.of(2010, 12, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Yota")
                        .setUrl("https://www.yota.ru/")
                        .setWorkPosition("Ведущий специалист")
                        .setEssenceOfWork("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2008, 6, 1))
                        .setTo(LocalDate.of(2010, 12, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Yota")
                        .setUrl("https://www.yota.ru/")
                        .setWorkPosition("Ведущий специалист")
                        .setEssenceOfWork("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2007, 3, 1))
                        .setTo(LocalDate.of(2008, 6, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Enkata")
                        .setUrl("http://enkata.com/")
                        .setWorkPosition("Разработчик ПО")
                        .setEssenceOfWork("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2005, 1, 1))
                        .setTo(LocalDate.of(2007, 2, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Siemens AG")
                        .setUrl("https://www.siemens.com/ru/ru/home.html")
                        .setWorkPosition("Разработчик ПО")
                        .setEssenceOfWork("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(1997, 9, 1))
                        .setTo(LocalDate.of(2005, 1, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Alcatel")
                        .setUrl("http://www.alcatel.ru/")
                        .setWorkPosition("Инженер по аппаратному и программному тестированию")
                        .setEssenceOfWork("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
                        .getResult());

        WorkStudyExperienceSection studySection = new WorkStudyExperienceSection();
        r.addSection(SectionType.EDUCATION, workSection);
        studySection.addItems(
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2013, 3, 1))
                        .setTo(LocalDate.of(2013, 5, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Coursera")
                        .setUrl("https://www.coursera.org/course/progfun")
                        .setWorkPosition("'Functional Programming Principles in Scala' by Martin Odersky")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2011, 3, 1))
                        .setTo(LocalDate.of(2011, 4, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Luxoft")
                        .setUrl("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")
                        .setWorkPosition("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(2005, 1, 1))
                        .setTo(LocalDate.of(2005, 4, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Siemens AG")
                        .setUrl("http://www.siemens.ru/")
                        .setWorkPosition("3 месяца обучения мобильным IN сетям (Берлин)")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(1997, 9, 1))
                        .setTo(LocalDate.of(1998, 3, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Alcatel")
                        .setUrl("http://www.alcatel.ru/")
                        .setWorkPosition("6 месяцев обучения цифровым телефонным сетям (Москва)")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(1993, 9, 1))
                        .setTo(LocalDate.of(1996, 7, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики")
                        .setUrl("http://www.ifmo.ru/")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(1993, 9, 1))
                        .setTo(LocalDate.of(1996, 7, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики")
                        .setUrl("http://www.ifmo.ru/")
                        .setWorkPosition("Аспирантура (программист С, С++)")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(1987, 9, 1))
                        .setTo(LocalDate.of(1993, 7, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики")
                        .setUrl("http://www.ifmo.ru/")
                        .setWorkPosition("Инженер (программист Fortran, C)")
                        .getResult(),
                WSEItemBuilder.getInstance()
                        .setFrom(LocalDate.of(1984, 9, 1))
                        .setTo(LocalDate.of(1987, 6, 1).with(TemporalAdjusters.lastDayOfMonth()))
                        .setOrganizationName("Заочная физико-техническая школа при МФТИ")
                        .setUrl("http://www.school.mipt.ru/")
                        .setWorkPosition("Закончил с отличием")
                        .getResult()
        );

    }
}
