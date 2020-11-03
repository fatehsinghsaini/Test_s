package com.os.busservice.model.busListResponse

data class Data(
    val available_sheat: Any,
    val available_sheat_sleeper: Any,
    val bus_driver_id: String,
    val bus_group: String,
    val bus_no: String,
    val bus_owner_name: String,
    val bus_rc_no: String,
    val bus_registration_no: String,
    val bus_route: BusRoute,
    val bus_type: BusType,
    val created_at: String,
    val end_time: String,
    val id: Int,
    val licence_expiry_date: String,
    val license_no: String,
    val license_no_file: String,
    val name: String,
    val other: String,
    val pollution_expiry_date: String,
    val pollution_no: String,
    val rc_no_file: String,
    val sheet_price: String,
    val sleeper_price: String,
    val start_time: String,
    val type_sheet: String
)