package com.bobwares.databus.common.rest;

import com.bobwares.core.security.authentication.AuthenticationService;
import com.bobwares.core.util.ObjectUtils;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.test.AbstractRestTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExampleReportRestServiceTest extends AbstractRestTest {
    @Inject
    AuthenticationService authenticationService;

    @Ignore
    @Test
    public void field() throws Exception {
        mockMvc.perform(
        	get("/rest/example-report/field")
        		.with(authToken("user1"))
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(7)))
            .andExpect(jsonPath("$[0].property", is("monthEarned")))
            .andExpect(jsonPath("$[1].property", is("earnedAmount")))
            .andExpect(jsonPath("$[2].property", is("expirationDate")))
            .andExpect(jsonPath("$[3].property", is("balance")))
            .andExpect(jsonPath("$[4].property", is("paxId")))
            .andExpect(jsonPath("$[5].property", is("moneyId")))
            .andExpect(jsonPath("$[6].property", is("dataSource")))
       ;
    }
    @Ignore
    @Test
    public void search() throws Exception {
        mockMvc.perform(
        	get("/rest/example-report/search")
        		.with(authToken("user1"))
        	)
	        .andExpect(status().isOk())
	        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	        .andExpect(jsonPath("$", hasSize(1)))
	        .andExpect(jsonPath("$[0]", hasSize(2)))
	        .andExpect(jsonPath("$[0][0].property", is("gmin")))
	        .andExpect(jsonPath("$[0][1].property", is("lastName")));
    }

    @Ignore
    @Test
    public void configuration() throws Exception {
        String json = mockMvc.perform(
        	get("/rest/example-report/configuration")
        		.with(authToken("user1"))
        	)
	        .andExpect(status().isOk())
	        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	        .andReturn().getResponse().getContentAsString();
        
        Configuration configuration = ObjectUtils.jsonToObject(json, Configuration.class);
        assertThat(configuration, notNullValue());
        
        assertThat(configuration.getTitle(), is("Example Report"));
        assertThat(configuration.getFields().size(), is(7));
    }

    /*
    @Test
    public void search_for_participants() throws Exception {
        mockMvc.perform(
                get("/rest/enrollment?filter-gmin=000000001&filter-lastName=Last")
                        .with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.itemCount", is(1)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].gmin", is("000000001")))
                .andExpect(jsonPath("$.items[0].lastName", is("Last")));
    }

    @Test
    public void getAll() throws Exception {
        // use all defaults
        authenticationService.authenticate("TID:000000");
        Integer programId = createProgram("TID:000000");

        // Establish permissions for this user to enroll people
        String preferredBAC = worksForService.getPreferredWorksForBAC();
        aclService.grantPermissionForPrincipal(programId, "ProgramActivity",
                "MMENRL@BAC:" + preferredBAC, "TID:000000");

        mockMvc.perform(
                get("/rest/enrollment?af-worksFor=481294&af-programId=" + programId)
                        .with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.startIndex", is(0)))
                .andExpect(jsonPath("$.endIndex", is(greaterThan(1))))
                .andExpect(jsonPath("$.itemCount", is(greaterThan(1))))
                .andExpect(jsonPath("$.items", hasSize(greaterThan(1)))) // default page size
                .andExpect(jsonPath("$.items[0].lastName", is(notNullValue())))
                .andExpect(jsonPath("$.items[0].roles", is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.items[0].enrolled", either(is("Y")).or(is("N"))));
    }

    @Test
    public void enroll() throws Exception {
        authenticationService.authenticate("TID:000000");
        Integer programId = createProgram("TID:000000");

        // Establish permissions for this user to enroll people
        String preferredBAC = worksForService.getPreferredWorksForBAC();
        aclService.grantPermissionForPrincipal(programId, "ProgramActivity",
                "MMENRL@BAC:" + preferredBAC, "TID:000000");

        // use all defaults
        mockMvc.perform(
                get("/rest/enrollment?af-worksFor=481294&af-programId=" + programId).with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.startIndex", is(0)))
                .andExpect(jsonPath("$.endIndex", is(greaterThan(1))))
                .andExpect(jsonPath("$.itemCount", is(greaterThan(1))))
                .andExpect(jsonPath("$.items", hasSize(greaterThan(1)))) // default page size
                .andExpect(jsonPath("$.items[0].lastName", is(notNullValue())))
                .andExpect(jsonPath("$.items[0].roles", is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.items[0].enrolled", either(is("Y")).or(is("N"))));

        mockMvc.perform(
                post("/rest/enrollment/enroll?id=399752&programId=" + programId).with(authToken("TID:000000"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GsonHelper.GSON.toJson(ImmutableMap.builder()
                                        .put("adminPermission", true)
                                        .put("enrollPermission", false)
                                        .put("voucherPermission", true)
                                        .build()
                        ))
        )
                .andDo(print())
                .andExpect(status().isOk());

        authenticationService.authenticate("TID:000000");
        assertThat(programEnrollService.isUserEnrolledInProgram(399752, programId), is(true));

        // verify permissions
        mockMvc.perform(
                get("/rest/enrollment/permissions?id=399752&programId=" + programId)
                        .with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminPermission", is(true)))
                .andExpect(jsonPath("$.enrollPermission", is(false)))
                .andExpect(jsonPath("$.voucherPermission", is(true)));

        mockMvc.perform(
                get("/rest/enrollment/unenroll?id=399752&programId=" + programId).with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk());

        authenticationService.authenticate("TID:000000");
        assertThat(programEnrollService.isUserEnrolledInProgram(399752, programId), is(false));
    }

    @Test
    public void permissionsForPermissions() throws Exception {
        authenticationService.authenticate("TID:000000");
        Integer programId = createProgram("TID:000000");

        // Establish permissions for this user
        String preferredBAC = worksForService.getPreferredWorksForBAC();
        aclService.grantPermissionForPrincipal(programId, "ProgramActivity",
                "MMENRL@BAC:" + preferredBAC, "TID:000000");

        // all permissions should be false to start with a new program
        mockMvc.perform(
                get("/rest/enrollment/permissions?id=399752&programId=" + programId)
                        .with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherPermission", is(false)))
                .andExpect(jsonPath("$.enrollPermission", is(false)));

        // set permissions to true
        Map<String, Object> map = Maps.newHashMap();
        map.put("adminPermission", Boolean.TRUE);
        map.put("enrollPermission", Boolean.TRUE);
        map.put("voucherPermission", Boolean.TRUE);

        mockMvc.perform(
                post("/rest/enrollment/permissions?id=399752&programId=" + programId)
                        .with(authToken("TID:000000"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GsonHelper.GSON.toJson(map))
        )
                .andDo(print())
                .andExpect(status().isOk());

        // verify permissions are true
        mockMvc.perform(
                get("/rest/enrollment/permissions?id=399752&programId=" + programId)
                        .with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherPermission", is(true)))
                .andExpect(jsonPath("$.enrollPermission", is(true)));

        // set permissions to false
        map.put("voucherPermission", Boolean.FALSE);
        map.put("adminPermission", Boolean.FALSE);
        map.put("enrollPermission", Boolean.FALSE);
        mockMvc.perform(
                post("/rest/enrollment/permissions?id=399752&programId=" + programId)
                        .with(authToken("TID:000000"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GsonHelper.GSON.toJson(map))
        )
                .andDo(print())
                .andExpect(status().isOk());

        // verify permissions are false
        mockMvc.perform(
                get("/rest/enrollment/permissions?id=399752&programId=" + programId)
                        .with(authToken("TID:000000")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherPermission", is(false)))
                .andExpect(jsonPath("$.enrollPermission", is(false)));

    }

    private static final Integer BAC_000001_paxId = 10;  // BAC000001

    @Test
    public void search_existing_user_and_add() throws Exception {
        String adminUser = "TID:000000";
        authenticationService.authenticate(adminUser);

        ParticipantProfile adminProfile = participantService.getProfile();
        Integer adminPaxId = adminProfile.getPax().getPaxId();
        Integer programId = createProgram(adminUser);


        // Establish permissions for the admin user to enroll people
        String preferredBAC = worksForService.getPreferredWorksForBAC();
        aclService.grantPermissionForPrincipal(programId, "ProgramActivity",
                "MMENRL@BAC:" + preferredBAC, adminUser);

        // verify permissions for adminUser
        mockMvc.perform(
                get("/rest/enrollment/permissions?id=" + adminPaxId + "&programId=" + programId)
                        .with(authToken(adminUser)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enrollPermission", is(true)));


        // get the list of people in this bac.  Should be empty
        // use all defaults
        mockMvc.perform(
                get("/rest/enrollment?af-programId=" + programId).with(authToken(adminUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.startIndex", is(0)))
                .andExpect(jsonPath("$.endIndex", is(greaterThan(1))))
                .andExpect(jsonPath("$.itemCount", is(greaterThan(1))))
                .andExpect(jsonPath("$.items", hasSize(greaterThan(1)))) // default page size
                .andExpect(jsonPath("$.items[0].lastName", is(notNullValue())))
//                .andExpect(jsonPath("$.items[0].roles", is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.items[0].enrolled", either(is("Y")).or(is("N"))));

        ParticipantProfile userProfile = participantService.getProfileByControlNum("GMIN:000000001");
        String gmin = userProfile.getPax().getTaxNum();
        Integer userPaxId = userProfile.getPax().getPaxId();

        // search for user 000000001 which should be found.  For a search, the autofilter for programId should
        // not be present
        String content = mockMvc.perform(
                get("/rest/enrollment?filter-gmin=" + gmin + "&filter-lastName=Last")
                        .with(authToken(adminUser)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.itemCount", is(1)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].id", is(userPaxId)))
                .andExpect(jsonPath("$.items[0].gmin", is(gmin)))
                .andExpect(jsonPath("$.items[0].lastName", is("Last")))
                .andReturn().getResponse().getContentAsString();


        // now enroll and set permissions for the user we just found
        mockMvc.perform(
                post("/rest/enrollment/enroll?id=" + userPaxId + "&programId=" + programId)
                        .with(authToken(adminUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GsonHelper.GSON.toJson(ImmutableMap.builder()
                                        .put("adminPermission", true)
                                        .put("enrollPermission", false)
                                        .put("voucherPermission", true)
                                        .build()
                        )))
//                .andDo(print())
                .andExpect(status().isOk());

        // verify the enrollment
        authenticationService.authenticate(adminUser);
        assertThat(programEnrollService.isUserEnrolledInProgram(userPaxId, programId), is(true));
    }

    @Test
    public void search_non_existing_user_and_add() throws Exception {
        String adminUser = "TID:000000";
        authenticationService.authenticate(adminUser);

        ParticipantProfile adminProfile = participantService.getProfile();
        Integer adminPaxId = adminProfile.getPax().getPaxId();
        Integer programId = createProgram(adminUser);


        // Establish permissions for the admin user to enroll people
        String preferredBAC = worksForService.getPreferredWorksForBAC();
        aclService.grantPermissionForPrincipal(programId, "ProgramActivity",
                "MMENRL@BAC:" + preferredBAC, adminUser);

        String gmin = "999999999";
        // search for user 999999999 which should not be found.  For a search, the autofilter for programId should
        // not be present
        mockMvc.perform(
                get("/rest/enrollment?filter-gmin=" + gmin + "&filter-lastName=Doe")
                        .with(authToken(adminUser)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.itemCount", is(0)))
                .andExpect(jsonPath("$.items", hasSize(0)));

        // add the user
        JSONObject person = new JSONObject();
        person.put("firstName", "Jane");
        person.put("lastName", "Doe");
        person.put("gmin", gmin);
        person.put("email", "john.doe@johndoe.com");

        ResultActions addResult = mockMvc.perform(
                post("/rest/enrollment?af-programId=" + programId).with(authToken(adminUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN)
                        .content((person.toJSONString())));
        addResult.andExpect(status().isOk())
                .andExpect(content().string(Matchers.startsWith("/enrollment/")));

        String result = addResult.andReturn().getResponse().getContentAsString();
        Integer newUserPaxId = Integer.valueOf(result.split("/")[2]);
        assertThat(newUserPaxId, is(Matchers.notNullValue()));

        mockMvc.perform(get("/rest" + result).with(authToken(adminUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(newUserPaxId)))
                .andExpect(jsonPath("$.firstName", is("Jane")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.gmin", is(gmin)))
                .andExpect(jsonPath("$.email", is("john.doe@johndoe.com")))
                .andExpect(jsonPath("$.dataSource", is(DataSource.MANUAL.name())))
                .andExpect(jsonPath("$.status", is("PENDING")));

        mockMvc.perform(delete("/rest/enrollment/" + newUserPaxId).with(authToken(adminUser)))
                .andExpect(status().isOk());
    }
    */
}