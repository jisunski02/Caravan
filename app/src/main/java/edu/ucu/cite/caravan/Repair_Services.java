package edu.ucu.cite.caravan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import edu.ucu.cite.caravan.databinding.ActivityMapsRepairShopBinding;

public class Repair_Services extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;

    private static final int REQUEST_CODE = 101;

    private ActivityMapsRepairShopBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 101;
    private double lat, lng;

    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();

    LatLng MendozaRadiatorRepairandOverhaul = new LatLng(16.1621778, 119.9703361);
    LatLng HiwayAutomotiveSupply = new LatLng(1561579, 119.978503);
    LatLng JardielBatteryShop = new LatLng(16.1520172, 119.9847571);
    LatLng JDAWeldingShop = new LatLng(16.1554695, 119.986193);
    LatLng Babstintpowerwindow = new LatLng(16.1468676, 119.9885515);
    LatLng CoolfedCarAirconSpecialist = new LatLng(16.1464435, 119.9888306);
    LatLng HiTechTireServiceCenter = new LatLng(16.1430384, 119.9907642);
    LatLng LeaamAutoCareCenter = new LatLng(16.1454764, 119.9894451);
    LatLng LuckyMAutoSupply = new LatLng(16.1468532, 119.9883012);
    LatLng StarmanEnterprisesInc = new LatLng(16.1437146, 119.9870609);
    LatLng MilkesAutoSupply = new LatLng(16.15261, 119.9854243);
    LatLng JRBDAutoRepairShop = new LatLng(16.152277, 119.9845779);
    LatLng GeminiAutoSupply = new LatLng(16.1538907, 119.9817001);
    LatLng ManantanElectricalandBatteryShop = new LatLng(16.156383, 119.9784803);
    LatLng JRBSBatteryShop = new LatLng(16.1562326, 119.9783984);
    LatLng GEDTireSupply = new LatLng(16.1562183, 119.9783246);
    LatLng MilkesAutoParts = new LatLng(16.1593991, 119.9751174);
    LatLng SRNMachineShop = new LatLng(15.8454042, 120.5309308);
    LatLng FloringsVulcanizingshop = new LatLng(15.8466815, 120.5231228);
    LatLng GENCARZ = new LatLng(16.0063574, 120.6675832);
    LatLng Superlyks = new LatLng(15.897768, 120.6746291);
    LatLng Osnubrtech = new LatLng(16.182395, 119.8622527);
    LatLng EspanolsVulcanizingShop = new LatLng(16.1841068, 119.8636832);
    LatLng MaAlexisAutoSupply = new LatLng(16.1840107, 119.8624009);
    LatLng TugadesVulcanizingShop = new LatLng(16.1850584, 119.8614578);
    LatLng MAAlexisAutoSupplyGenMerchandise = new LatLng(16.1946048, 119.864847);
    LatLng ErningsVulcanizingShop = new LatLng(15.8802649, 120.3890245);
    LatLng CAGUNOTCOMPOUND = new LatLng(15.883933, 120.3962084);
    LatLng ChrisJunioAutoShop = new LatLng(15.8109931, 120.4682502);
    LatLng SevenGarage = new LatLng(15.8345635, 120.4643261);
    LatLng TEJ477AutoShop = new LatLng(15.8256654, 120.4567929);
    LatLng ArmanMechanic = new LatLng(15.8199558, 120.4574275);
    LatLng FilipinasAutoSupply = new LatLng(15.815899, 120.455228);
    LatLng DeVeraFrancisAutoSupply = new LatLng(15.815465, 120.455062);
    LatLng TireCenter = new LatLng(15.8144922, 120.4555971);
    LatLng ArjayAutoElectricalShop = new LatLng(15.7921716, 120.455366);
    LatLng VulcanizingShop = new LatLng(15.7804716, 120.4495803);
    LatLng DusocVulcanizingShop = new LatLng(15.7849364, 120.4430589);
    LatLng MJLAUTOCARE = new LatLng(15.8450098, 120.4093036);
    LatLng PetersVulcanizingShop = new LatLng(15.8105725, 120.4615031);
    LatLng MPJAutoSupply = new LatLng(16.0538928, 120.5912199);
    LatLng ZapZapWeldingShop = new LatLng(16.0466412, 120.5976641);
    LatLng JfbTireCenter = new LatLng(16.0172549, 120.5795069);
    LatLng VillaRosita = new LatLng(16.0398503, 120.5923516);
    LatLng BeltranAutoSupplyPetronGasul = new LatLng(16.0464314, 120.5954212);
    LatLng KcCAutoSupply = new LatLng(16.044454, 120.5859648);
    LatLng DMAGGeneneralMerchandise = new LatLng(16.0082126, 120.2856732);
    LatLng JasBatteryShop = new LatLng(16.0138661, 120.3087229);
    LatLng Pipers = new LatLng(16.3874003, 119.8924202);
    LatLng QuebadaStore = new LatLng(16.3851156, 119.8931127);
    LatLng CabuyaoAutoRepairShop = new LatLng(16.3399593, 119.8926884);
    LatLng Cruzelectrical = new LatLng(15.9769228, 120.2198069);
    LatLng RelmarcAutoShop = new LatLng(15.9711684, 120.2139251);
    LatLng JanjanAutoSupply = new LatLng(15.9503776, 120.2175595);
    LatLng PedyTarectecanAutoElectricalShop = new LatLng(16.05757, 119.8689683);
    LatLng beepzautorepairshop = new LatLng(15.9963829, 120.3602945);
    LatLng JavierAutoServices = new LatLng(15.9972544, 120.3604849);
    LatLng CastrolCarWorkshopNistecMotorShop = new LatLng(16.0123102, 120.3567159);
    LatLng AljeannAutoSupplyMotors = new LatLng(16.020772, 120.3541678);
    LatLng KCAutoShop = new LatLng(16.0209818, 120.3461245);
    LatLng PhookzThaiPartsandAccessories = new LatLng(15.9695862, 120.3560025);
    LatLng AutosmartPhilippines = new LatLng(16.0214077, 120.3462753);
    LatLng JPBVulcanizing = new LatLng(16.0208313, 120.3697971);
    LatLng BoltInAutoRepairShop = new LatLng(16.0206869, 120.3699982);
    LatLng DBVulcanizingShop = new LatLng(16.0206624, 120.3700519);
    LatLng JacobShop = new LatLng(15.9784178, 120.3575033);
    LatLng TulaganAutoMechanicalRepairShop = new LatLng(16.0205091, 120.3706339);
    LatLng RicksTireSupplyVulcanizingShop = new LatLng(16.0204707, 120.3707354);
    LatLng JunAutoRepairShopMetalCraft = new LatLng(16.0180076, 120.3724051);
    LatLng ZaldyAutoElectricalShopBatteryCharging = new LatLng(16.0190653, 120.3736165);
    LatLng CycleHouse = new LatLng(16.0187933, 120.3754183);
    LatLng RVMTireBatteryandVulcanizingShop = new LatLng(16.0189158, 120.3747382);
    LatLng AlmarsAutoTechCarAutoElectrical = new LatLng(16.0181814, 120.3765845);
    LatLng VulcanizingShopinBuedEast = new LatLng(16.0161418, 120.3840503);
    LatLng JDJWeldingandRepairShop = new LatLng(16.0151616, 120.3872772);
    LatLng UcarAutoSupply = new LatLng(16.0149378, 120.387036);
    LatLng MarianoElectricalShop = new LatLng(15.971479, 120.3810972);
    LatLng NickAutoRepairShop = new LatLng(15.9719496, 120.3810234);
    LatLng RJKarrHausGeneralAutoServiceCenter = new LatLng(15.9526749, 120.398084);
    LatLng CalasiaoConceptTireTrading = new LatLng(15.9651838, 120.3863703);
    LatLng CarmarkLubricants = new LatLng(15.9575169, 120.3936127);
    LatLng MANNELECTROMECHANICALPARTSSERVICES = new LatLng(16.0213401, 120.3495527);
    LatLng TRIPLEMAUTOSUPPLY = new LatLng(16.0214861, 120.3487954);
    LatLng NEWCARDOZOAUTOREPAIRSHOP = new LatLng(16.0205045, 120.3491665);
    LatLng MRCTIRESUPPLY = new LatLng(16.0215093, 120.3482267);
    LatLng BRYANALBERTTINT = new LatLng(16.0215067, 120.3474757);
    LatLng LansanganVulcanizingShop = new LatLng(16.0197028, 120.3429831);
    LatLng LAGSAutoSupplySurplusAutoParts = new LatLng(16.0190231, 120.3403462);
    LatLng aTLaSCARSOLUTIONS = new LatLng(16.0136763, 120.3644445);
    LatLng VulcanizingShop1 = new LatLng(15.9959134, 120.3608117);
    LatLng AljeannAutoSupplyandMotorWorks = new LatLng(16.0468854, 120.3514052);
    LatLng JoemarSuspension = new LatLng(16.0463136, 120.3470567);
    LatLng Lagaweifugao = new LatLng(16.0976374, 120.3776769);
    LatLng JrmAutopaint = new LatLng(16.0284434, 120.3284573);
    LatLng SoyAutoSupply = new LatLng(16.0435154, 120.3290113);
    LatLng MichelinTires = new LatLng(16.0462199, 120.3523376);
    LatLng HungATiresPhilippines = new LatLng(16.0463075, 120.3523404);
    LatLng BFGoodrichTiresPhilippines = new LatLng(16.0463304, 120.3523411);
    LatLng CooperTiresPhilippines = new LatLng(16.04655, 120.3525154);
    LatLng RycoInc = new LatLng(16.0465884, 120.3525779);
    LatLng CarloTireSupplyAuthorizedDealerofFederalTires = new LatLng(16.0466138, 120.3527388);
    LatLng NoyAutoElectrical = new LatLng(16.0464237, 120.352994);
    LatLng SolimanMachineWorks = new LatLng(16.0421737, 120.3571451);
    LatLng CaravelleAutoSupply = new LatLng(16.0226788, 120.3466553);
    LatLng WHAUTOSUPPLY = new LatLng(16.0379213, 120.3331966);
    LatLng Shell = new LatLng(16.0365459, 120.3321295);
    LatLng GianCarloTireSupply = new LatLng(16.0295199, 120.3290228);
    LatLng MTAutoSupply = new LatLng(16.0204757, 120.3302663);
    LatLng RciTireSupply = new LatLng(16.0246805, 120.3266357);
    LatLng DansCarAudioAccessoriesAuthorizedDealerofFederalTires = new LatLng(16.0229744, 120.3260115);
    LatLng BoschCarService = new LatLng(16.0229326, 120.3257851);
    LatLng CjfBatteryDepotInc = new LatLng(16.021498, 120.3247209);
    LatLng RjksMelAutoRepairShop = new LatLng(16.0221524, 120.3222637);
    LatLng SantysAutoRepairShop = new LatLng(16.0206857, 120.3228924);
    LatLng BinsMarketing = new LatLng(15.9572752, 119.8042204);
    LatLng CarmakDasol = new LatLng(15.989939, 119.8807463);
    LatLng Samzonmagno = new LatLng(15.9843165, 119.8726999);
    LatLng FixCarAutoCareCenter = new LatLng(15.9960114, 120.1633098);
    LatLng LMPAutoCarCare = new LatLng(16.0099155, 120.226209);
    LatLng ArjieElectronicsShop = new LatLng(16.0263018, 120.2493574);
    LatLng WestShopPh = new LatLng(16.027043, 120.2214957);
    LatLng MananganMachineShop = new LatLng(16.001848, 120.2245528);
    LatLng AstroEmissionTestingCenter = new LatLng(16.0315666, 120.2334677);
    LatLng MarkWorkz = new LatLng(16.0239451, 120.221475);
    LatLng FgrBatteryCare = new LatLng(16.0094465, 120.2245806);
    LatLng JamtasAutoSupplySurplus = new LatLng(16.0244446, 120.2414578);
    LatLng VirayTowingService = new LatLng(16.0230045, 120.2374434);
    LatLng BOSCOVilicasTractorandAutoParts = new LatLng(16.0228344, 120.2373536);
    LatLng MarcosRepairShop = new LatLng(16.0211742, 120.2292573);
    LatLng AgberAutoParts = new LatLng(16.0223474, 120.2347076);
    LatLng MagallanesAutoParts = new LatLng(16.022169, 120.234957);
    LatLng CivicAutoSupply = new LatLng(16.0208571, 120.2326812);
    LatLng BlaztingboyzPowdercoatingSandblastingServices = new LatLng(15.8862015, 120.4302573);
    LatLng JaymarksRepairShop = new LatLng(15.8802822, 120.4519377);
    LatLng ANNJIEAUTOSERVICECENTER = new LatLng(15.9198138, 120.4206843);
    LatLng Magic8AutoSupply = new LatLng( 15.919342, 120.416315);
    LatLng BAutoSupplyGeneralMdse = new LatLng(15.9196196, 120.4194229);
    LatLng GMCycleYamaha3sManaoagBranch = new LatLng(16.0438338, 120.4844466);
    LatLng AerielleThaiShop = new LatLng(16.054002, 120.5109198);
    LatLng MArotaAutoSupply = new LatLng(16.0438605, 120.4841139);
    LatLng EagleFinancialServicesGroupIncorporated = new LatLng( 16.0440399, 120.4845685);
    LatLng FerecorAutoSupply = new LatLng(16.0431328, 120.4847339);
    LatLng TWINRIDERSGEARHUB = new LatLng(16.0440911, 120.4856623);
    LatLng JsAutoUpholstery = new LatLng(16.0524068, 120.4656341);
    LatLng JuroAutoSupply = new LatLng( 16.0458109, 120.4807654);
    LatLng AcruxGeneralAutoSupply = new LatLng( 16.0458629, 120.4812144);
    LatLng PrecisionMechanicsAutocareCenter = new LatLng(16.0614914, 120.4015987);
    LatLng PangasinanEmissionTestingCenter = new LatLng(16.0487877, 120.372329);
    LatLng GajahTunggalTireCenter = new LatLng( 16.0491799, 120.3730935);
    LatLng GuiaoTireSupplyShop = new LatLng(16.0520969, 120.3825697);
    LatLng PERTECHEnterprises = new LatLng(16.0898488, 120.3915739);
    LatLng GabrielAutoSupply = new LatLng(16.0557659, 120.3903131);
    LatLng ALLANANDSIDSCARWASH = new LatLng(16.0579594, 120.393961);
    LatLng CycleHouseMangaldan = new LatLng(16.0826836, 120.3959603);
    LatLng VulcanizingShop2 = new LatLng(16.0805219, 120.3972065);
    LatLng Triple8AutoSupply = new LatLng(16.0599441, 120.3991836);
    LatLng EfaAutoSupply = new LatLng(16.0767258, 120.3998524);
    LatLng CentropartsAutoSupplyAuthorizedDealerofFederalandHankookTires = new LatLng(16.0606891, 120.4005012);
    LatLng MejiasMachineShop = new LatLng(16.0612198, 120.4014525);
    LatLng RGAutoSupply = new LatLng(16.0714394, 120.4031094);
    LatLng RandwickTiresWheelsService = new LatLng(16.0635997, 120.4031993);
    LatLng UmanganMangataremPangasinan = new LatLng(15.7973787, 120.2866);
    LatLng MotoliteBatteryDepotMangatarem = new LatLng(15.7945664, 120.2899334);
    LatLng ABISUV2343 = new LatLng(15.7917925, 120.2919942);
    LatLng EdrianStevenCycleParts = new LatLng(15.7916796, 120.2947457);
    LatLng MRPOQZAUTOSPAEXPRESS = new LatLng(15.7790485, 120.3075901);
    LatLng GMRVulcanizing = new LatLng(16.109937, 120.552657);
    LatLng MelanioMechanic = new LatLng(16.1058466, 120.5595696);
    LatLng JamiedineAutoParts = new LatLng( 16.109504, 120.543832);
    LatLng AlfredsTireSupplyVulcanizing = new LatLng(16.1321523, 120.52891);
    LatLng PierceMotorParts = new LatLng(16.1093101, 120.5440987);
    LatLng RafaelAutoParts = new LatLng(16.1116644, 120.5442278);
    LatLng BotesBatteryServiceShop = new LatLng(16.1119105, 120.5444002);
    LatLng AltheaNorthernRetreadersEnterprises = new LatLng(16.117162, 120.542142);
    LatLng ALTHEATireRecapping = new LatLng(16.1135777, 120.5470803);
    LatLng CastrolCarWorkshop5SpeedAutoCareCorp = new LatLng(15.8928246, 120.6120886);
    LatLng SMSAutoSupply = new LatLng(15.8928258, 120.6120899);
    LatLng BustosAutoSupply = new LatLng(15.8940027, 120.6310754);
    LatLng BustosAutoSupplyAuthorizedDealerofFederalandHankookTires = new LatLng(15.8857124, 120.5978213);
    LatLng CRISANTOCarAirconSpecialistTintPowerWindowAlarmCentralLockStereoElectricalSuspensionUnderChassisCamberAlignWiggleCalampag = new LatLng(15.8811028, 120.5998267);
    LatLng MarlonAutoSupply = new LatLng(15.8858405, 120.5992386);
    LatLng ChanAutoAirconMechanic = new LatLng(15.8796775, 120.6006437);
    LatLng EpsanAutoSupply = new LatLng(15.9062059, 120.6377087);
    LatLng RjbCarAircon = new LatLng(15.8857501, 120.6001576);
    LatLng GoodBoyTiresService = new LatLng(15.8859465, 120.6001448);
    LatLng HAndDAutoServiceShop = new LatLng(15.8941247, 120.6142267);
    LatLng JDiazAutoSupply = new LatLng(15.895884, 120.628687);
    LatLng TotalQuartzAutoCare  = new LatLng(15.893877, 120.6309192);
    LatLng StarmanTireSupplySanCarlosCity = new LatLng(15.9392409, 120.3455982);
    LatLng Nanofilmsceramicwindowfilmsphilippines = new LatLng(15.9489937, 120.34702);
    LatLng OnyokWorks = new LatLng( 15.9583891, 120.3499535);
    LatLng ZIPTHAIPARTS = new LatLng(15.8914681, 120.376685);
    LatLng RodamAutoRepairShop = new LatLng(15.9416845, 120.336952);
    LatLng JazNJackieenterprise = new LatLng(15.9371075, 120.3468561);
    LatLng SebastCycleParts = new LatLng(15.9370152, 120.3461541);
    LatLng TIRETECHTIREAUTOSUPPLY = new LatLng(15.9360842, 120.3469433);
    LatLng RZAutoSupply = new LatLng(15.9305155, 120.3483563);
    LatLng ZenAutoSupply = new LatLng(15.9297602, 120.3479668);
    LatLng JlSorianoAutoElectrical = new LatLng(15.9296595, 120.3479547);
    LatLng DomRocasTire = new LatLng(16.1180711, 120.4005465);
    LatLng MikesWeldingandAutoBodyRepairShop = new LatLng(16.128816, 120.40815);
    LatLng RomyAutoRepairShop = new LatLng(16.13084, 120.4099881);
    LatLng LDSCARWASHANDMOTORWORKS = new LatLng(16.1014524, 120.3937789);
    LatLng CarAirConAutoRepairShop = new LatLng(16.0625623, 120.649173);
    LatLng TripleA = new LatLng(16.0637169, 120.6685652);
    LatLng JustandJam = new LatLng(16.0652209, 120.6715101);
    LatLng ThaicoonCycleCenter = new LatLng(15.9897355, 120.8048349);
    LatLng TKtiresupplyandvulcanizingshop = new LatLng( 15.9820514, 120.8114999);
    LatLng IESHOPAutoElectricalVulcanizingWelding = new LatLng(15.9795838, 120.8136584);
    LatLng ONATOEMANILTRADING = new LatLng(15.982108, 120.8145828);
    LatLng MagentaAutoSupply = new LatLng(16.0690798, 120.4401364);
    LatLng BlackoutSpeedShop = new LatLng(16.0699338, 120.7597884);
    LatLng AutobotzAutoWorks = new LatLng(16.0714829, 120.7622337);
    LatLng JaeEnterprisesTireSupplyAndVulcanizingShop = new LatLng(16.1655132, 120.5204072);
    LatLng PURM2RCyclePartsAccessories = new LatLng(16.1607833, 120.5213931);
    LatLng TravellersAutoSupply = new LatLng(16.1555954, 120.5219792);

    LatLng CemcarTireShop = new LatLng( 16.0124962, 120.3925609);
    LatLng ChefsAutoSupply = new LatLng(16.0094767, 120.3984964);
    LatLng IrishAutoParts = new LatLng(16.0073302, 120.4026211);
    LatLng MundraAutoSupply = new LatLng(16.007536, 120.404449);
    LatLng AutoCrewCarwashandAutoDetailing = new LatLng( 16.00584, 120.4038341);
    LatLng PartnersAutoElectricalRadiatorShop = new LatLng(15.996243, 120.4061008);
    LatLng MiamiAutoSupplyandGeneralMerchandise = new LatLng(15.9966504, 120.4067765);
    LatLng JeffAutoRepairShop = new LatLng(15.9983053, 120.4160265);
    LatLng AllansAutoRepairShop = new LatLng(16.0705553, 120.0933257);
    LatLng Nproject = new LatLng(16.0127113, 120.720119);
    LatLng TransCycle = new LatLng(16.0271301, 120.746274);
    LatLng WilbelBatterySupply = new LatLng(16.0246703, 120.7466004);
    LatLng JsRadiatorShop = new LatLng(16.0232872, 120.7472054);
    LatLng PerezBatteryShop = new LatLng(16.0212102, 120.7451753);
    LatLng JoyAutoSupply = new LatLng(16.0183065, 120.7419677);
    LatLng CanaveralAutoServiceCenter = new LatLng(16.0174336, 120.7410242);
    LatLng NorthFoilAndRetrofit = new LatLng(15.9220936, 120.8473986);
    LatLng NAutoElectricalSupply = new LatLng(15.9320701, 120.8424532);
    LatLng JCToriosAutoRepairWeldingShop = new LatLng(15.9145345, 120.7707101);
    LatLng AUTOWOKZSERVICELTD = new LatLng(15.9173214, 120.8188234);
    LatLng BigBrosTireOutlet = new LatLng(15.96098, 120.5739139);
    LatLng LonAutoSupply = new LatLng(15.9598421, 120.5744633);
    LatLng JrRicCars = new LatLng(15.9557692, 120.5758313);
    LatLng TATAREYAUTOPAINTAUTOREPAIRAIRCONREPAIR = new LatLng(15.9956329, 120.5772136);
    LatLng InzaidwayCarTintandAccessories = new LatLng(15.97896, 120.571072);
    LatLng CastrolCarWorkshopCHECKPOINTSALESAUTOSOLUTIONSINC = new LatLng(15.9739249, 120.5711947);
    LatLng Km183EngineeringWorksEngineServices = new LatLng(15.9874281, 120.5727405);
    LatLng StarmanTireSupply = new LatLng(15.9932251, 120.5741327);
    LatLng BestoAutoSupply = new LatLng(15.9734012, 120.5707432);
    LatLng RACSAutomotiveUrdaneta = new LatLng(16.0117551, 120.5724423);
    LatLng UniversalAutoSupply = new LatLng(15.9759647, 120.5757862);
    LatLng HondaServiceCenter = new LatLng(15.9744851, 120.5502155);
    LatLng MaxmasterEnterprises = new LatLng(15.979695, 120.5726641);
    LatLng CKCyclePartsGenMdse = new LatLng(15.978862, 120.5666772);
    LatLng RCTireSupply = new LatLng(15.9465633, 120.5801778);
    LatLng SrjCarAutoPaintingShop = new LatLng( 15.9485846, 120.5786723);
    LatLng ROADMASTERMUFFLERCENTER = new LatLng(15.9504105, 120.5776723);
    LatLng GSBatterySupply = new LatLng(15.952899, 120.576454);
    LatLng JandreiAutoParts = new LatLng(15.958126, 120.5751591);
    LatLng FastLubesAutomotiveCenter = new LatLng(15.9684274, 120.5717877);

    LatLng SANCarAirconSpecialist = new LatLng(15.9678696, 120.5724636);
    LatLng TopstarTireSupply = new LatLng(15.9690138, 120.5717756);
    LatLng Station5GasandService = new LatLng(15.9695209, 120.571611);
    LatLng AutoMagicTintandCarAirconService = new LatLng(15.9685527, 120.5725781);
    LatLng EvansCarwashDetailingCenter = new LatLng(15.8268894, 120.3349109);
    LatLng DenreedSapinoso = new LatLng(15.9395528, 120.6253968);
    LatLng RUGIESCARCENTERBARANGAYBACAG = new LatLng(15.9285742, 120.5802125);
    LatLng DarwinBoyAutoSupplyAndGeneralMerchandise = new LatLng(15.9165025, 120.5835259);
    LatLng SnRAutoShop = new LatLng(15.9120024, 120.5842977);
    LatLng RNRAutoRepairCenter = new LatLng(15.9119261, 120.5845937);
    LatLng CamillesCyclesandTrends = new LatLng(15.903163, 120.586558);
    LatLng JeshaiahsTrading = new LatLng(15.9022142, 120.5871204);
    LatLng RpsAutoSupply = new LatLng(15.8961307, 120.5916239);
    LatLng UncleGreyThaiparts = new LatLng(15.9042405, 120.5980374);
    LatLng JMAUTOZONECUSTOMPAINTSHOP = new LatLng(15.8924203, 120.5935764);
    LatLng  RugiesCarCenter = new LatLng( 15.9411825, 120.5810559);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair__services);
     //obtain the supportmapfragment

        binding = ActivityMapsRepairShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

        arrayList.add(MendozaRadiatorRepairandOverhaul);
        arrayList.add(HiwayAutomotiveSupply);
        arrayList.add(JardielBatteryShop);
        arrayList.add(JDAWeldingShop);
        arrayList.add(Babstintpowerwindow);
        arrayList.add(CoolfedCarAirconSpecialist);
        arrayList.add(HiTechTireServiceCenter);
        arrayList.add(LeaamAutoCareCenter);
        arrayList.add(LuckyMAutoSupply);
        arrayList.add(StarmanEnterprisesInc);
        arrayList.add(MilkesAutoSupply);
        arrayList.add(JRBDAutoRepairShop);
        arrayList.add(GeminiAutoSupply);
        arrayList.add(ManantanElectricalandBatteryShop);
        arrayList.add(JRBSBatteryShop);
        arrayList.add(GEDTireSupply);
        arrayList.add(MilkesAutoParts);
        arrayList.add(SRNMachineShop);
        arrayList.add(FloringsVulcanizingshop);
        arrayList.add(GENCARZ);
        arrayList.add(Superlyks);
        arrayList.add(Osnubrtech);
        arrayList.add(EspanolsVulcanizingShop);
        arrayList.add(MaAlexisAutoSupply);
        arrayList.add(TugadesVulcanizingShop);
        arrayList.add(MAAlexisAutoSupplyGenMerchandise);
        arrayList.add(ErningsVulcanizingShop);
        arrayList.add(CAGUNOTCOMPOUND);
        arrayList.add(ChrisJunioAutoShop);
        arrayList.add(SevenGarage);
        arrayList.add(TEJ477AutoShop);
        arrayList.add(ArmanMechanic);
        arrayList.add(FilipinasAutoSupply);
        arrayList.add(DeVeraFrancisAutoSupply);
        arrayList.add(TireCenter);
        arrayList.add(ArjayAutoElectricalShop);
        arrayList.add(VulcanizingShop);
        arrayList.add(DusocVulcanizingShop);
        arrayList.add(MJLAUTOCARE);
        arrayList.add(PetersVulcanizingShop);
        arrayList.add(MPJAutoSupply);
        arrayList.add(ZapZapWeldingShop);
        arrayList.add(JfbTireCenter);
        arrayList.add(VillaRosita);
        arrayList.add(BeltranAutoSupplyPetronGasul);
        arrayList.add(KcCAutoSupply);
        arrayList.add(DMAGGeneneralMerchandise);
        arrayList.add(JasBatteryShop);
        arrayList.add(Pipers);
        arrayList.add(QuebadaStore);
        arrayList.add(CabuyaoAutoRepairShop);
        arrayList.add(Cruzelectrical);
        arrayList.add(RelmarcAutoShop);
        arrayList.add(JanjanAutoSupply);
        arrayList.add(PedyTarectecanAutoElectricalShop);
        arrayList.add(beepzautorepairshop);
        arrayList.add(JavierAutoServices);
        arrayList.add(CastrolCarWorkshopNistecMotorShop);
        arrayList.add(AljeannAutoSupplyMotors);
        arrayList.add(KCAutoShop);
        arrayList.add(PhookzThaiPartsandAccessories);
        arrayList.add(AutosmartPhilippines);
        arrayList.add(JPBVulcanizing);
        arrayList.add(BoltInAutoRepairShop);
        arrayList.add(DBVulcanizingShop);
        arrayList.add(JacobShop);
        arrayList.add(TulaganAutoMechanicalRepairShop);
        arrayList.add(RicksTireSupplyVulcanizingShop);
        arrayList.add(JunAutoRepairShopMetalCraft);
        arrayList.add(ZaldyAutoElectricalShopBatteryCharging);
        arrayList.add(CycleHouse);
        arrayList.add(RVMTireBatteryandVulcanizingShop);
        arrayList.add(AlmarsAutoTechCarAutoElectrical);
        arrayList.add(VulcanizingShopinBuedEast);
        arrayList.add(JDJWeldingandRepairShop);
        arrayList.add(UcarAutoSupply);
        arrayList.add(MarianoElectricalShop);
        arrayList.add(NickAutoRepairShop);
        arrayList.add(RJKarrHausGeneralAutoServiceCenter);
        arrayList.add(CalasiaoConceptTireTrading);
        arrayList.add(CarmarkLubricants);
        arrayList.add(MANNELECTROMECHANICALPARTSSERVICES);
        arrayList.add(TRIPLEMAUTOSUPPLY);
        arrayList.add(NEWCARDOZOAUTOREPAIRSHOP);
        arrayList.add(MRCTIRESUPPLY);
        arrayList.add(BRYANALBERTTINT);
        arrayList.add(LansanganVulcanizingShop);
        arrayList.add(LAGSAutoSupplySurplusAutoParts);
        arrayList.add(aTLaSCARSOLUTIONS);
        arrayList.add(VulcanizingShop1);
        arrayList.add(AljeannAutoSupplyandMotorWorks);
        arrayList.add(JoemarSuspension);
        arrayList.add(Lagaweifugao);
        arrayList.add(JrmAutopaint);
        arrayList.add(SoyAutoSupply);
        arrayList.add(MichelinTires);
        arrayList.add(HungATiresPhilippines);
        arrayList.add(BFGoodrichTiresPhilippines);
        arrayList.add(CooperTiresPhilippines);
        arrayList.add(RycoInc);
        arrayList.add(CarloTireSupplyAuthorizedDealerofFederalTires);
        arrayList.add(NoyAutoElectrical);
        arrayList.add(SolimanMachineWorks);
        arrayList.add(CaravelleAutoSupply);
        arrayList.add(WHAUTOSUPPLY);
        arrayList.add(Shell);
        arrayList.add(GianCarloTireSupply);
        arrayList.add(MTAutoSupply);
        arrayList.add(RciTireSupply);
        arrayList.add(DansCarAudioAccessoriesAuthorizedDealerofFederalTires);
        arrayList.add(BoschCarService);
        arrayList.add(CjfBatteryDepotInc);
        arrayList.add(RjksMelAutoRepairShop);
        arrayList.add(SantysAutoRepairShop);
        arrayList.add(BinsMarketing);
        arrayList.add(CarmakDasol);
        arrayList.add(Samzonmagno);
        arrayList.add(FixCarAutoCareCenter);
        arrayList.add(LMPAutoCarCare);
        arrayList.add(ArjieElectronicsShop);
        arrayList.add(WestShopPh);
        arrayList.add(MananganMachineShop);
        arrayList.add(AstroEmissionTestingCenter);
        arrayList.add(MarkWorkz);
        arrayList.add(FgrBatteryCare);
        arrayList.add(JamtasAutoSupplySurplus);
        arrayList.add(VirayTowingService);
        arrayList.add(BOSCOVilicasTractorandAutoParts);
        arrayList.add(MarcosRepairShop);
        arrayList.add(AgberAutoParts);
        arrayList.add(MagallanesAutoParts);
        arrayList.add(CivicAutoSupply);
        arrayList.add(BlaztingboyzPowdercoatingSandblastingServices);
        arrayList.add(JaymarksRepairShop);
        arrayList.add(ANNJIEAUTOSERVICECENTER);
        arrayList.add(Magic8AutoSupply);
        arrayList.add(BAutoSupplyGeneralMdse);
        arrayList.add(GMCycleYamaha3sManaoagBranch);
        arrayList.add(AerielleThaiShop);
        arrayList.add(MArotaAutoSupply);
        arrayList.add(EagleFinancialServicesGroupIncorporated);
        arrayList.add(FerecorAutoSupply);
        arrayList.add(TWINRIDERSGEARHUB);
        arrayList.add(JsAutoUpholstery);
        arrayList.add(JuroAutoSupply);
        arrayList.add(AcruxGeneralAutoSupply);
        arrayList.add(PrecisionMechanicsAutocareCenter);
        arrayList.add(PangasinanEmissionTestingCenter);
        arrayList.add(GajahTunggalTireCenter);
        arrayList.add(GuiaoTireSupplyShop);
        arrayList.add(PERTECHEnterprises);
        arrayList.add(GabrielAutoSupply);
        arrayList.add(ALLANANDSIDSCARWASH);
        arrayList.add(CycleHouseMangaldan);
        arrayList.add(VulcanizingShop2);
        arrayList.add(Triple8AutoSupply);
        arrayList.add(EfaAutoSupply);
        arrayList.add(CentropartsAutoSupplyAuthorizedDealerofFederalandHankookTires);
        arrayList.add(MejiasMachineShop);
        arrayList.add(RGAutoSupply);
        arrayList.add(RandwickTiresWheelsService);
        arrayList.add(UmanganMangataremPangasinan);
        arrayList.add(MotoliteBatteryDepotMangatarem);
        arrayList.add(ABISUV2343);
        arrayList.add(EdrianStevenCycleParts);
        arrayList.add(MRPOQZAUTOSPAEXPRESS);
        arrayList.add(GMRVulcanizing);
        arrayList.add(MelanioMechanic);
        arrayList.add(JamiedineAutoParts);
        arrayList.add(AlfredsTireSupplyVulcanizing);
        arrayList.add(PierceMotorParts);
        arrayList.add(RafaelAutoParts);
        arrayList.add(BotesBatteryServiceShop);
        arrayList.add(AltheaNorthernRetreadersEnterprises);
        arrayList.add(ALTHEATireRecapping);
        arrayList.add(CastrolCarWorkshop5SpeedAutoCareCorp);
        arrayList.add(SMSAutoSupply);
        arrayList.add(BustosAutoSupply);
        arrayList.add(BustosAutoSupplyAuthorizedDealerofFederalandHankookTires);
        arrayList.add(CRISANTOCarAirconSpecialistTintPowerWindowAlarmCentralLockStereoElectricalSuspensionUnderChassisCamberAlignWiggleCalampag);
        arrayList.add(MarlonAutoSupply);
        arrayList.add(ChanAutoAirconMechanic);
        arrayList.add(EpsanAutoSupply);
        arrayList.add(RjbCarAircon);
        arrayList.add(GoodBoyTiresService);
        arrayList.add(HAndDAutoServiceShop);
        arrayList.add(JDiazAutoSupply);
        arrayList.add(TotalQuartzAutoCare);
        arrayList.add(StarmanTireSupplySanCarlosCity);
        arrayList.add(Nanofilmsceramicwindowfilmsphilippines);
        arrayList.add(OnyokWorks);
        arrayList.add(ZIPTHAIPARTS);
        arrayList.add(RodamAutoRepairShop);
        arrayList.add(JazNJackieenterprise);
        arrayList.add(SebastCycleParts);
        arrayList.add(TIRETECHTIREAUTOSUPPLY);
        arrayList.add(RZAutoSupply);
        arrayList.add(ZenAutoSupply);
        arrayList.add(JlSorianoAutoElectrical);
        arrayList.add(DomRocasTire);
        arrayList.add(MikesWeldingandAutoBodyRepairShop);
        arrayList.add(RomyAutoRepairShop);
        arrayList.add(LDSCARWASHANDMOTORWORKS);
        arrayList.add(CarAirConAutoRepairShop);
        arrayList.add(TripleA);
        arrayList.add(JustandJam);
        arrayList.add(ThaicoonCycleCenter);
        arrayList.add(TKtiresupplyandvulcanizingshop);
        arrayList.add(IESHOPAutoElectricalVulcanizingWelding);
        arrayList.add(ONATOEMANILTRADING);
        arrayList.add(MagentaAutoSupply);
        arrayList.add(BlackoutSpeedShop);
        arrayList.add(AutobotzAutoWorks);
        arrayList.add(JaeEnterprisesTireSupplyAndVulcanizingShop);
        arrayList.add(PURM2RCyclePartsAccessories);
        arrayList.add(TravellersAutoSupply);

        arrayList.add(CemcarTireShop);
        arrayList.add(ChefsAutoSupply);
        arrayList.add(IrishAutoParts);
        arrayList.add(MundraAutoSupply);
        arrayList.add(AutoCrewCarwashandAutoDetailing);
        arrayList.add(PartnersAutoElectricalRadiatorShop);
        arrayList.add(MiamiAutoSupplyandGeneralMerchandise);
        arrayList.add(JeffAutoRepairShop);
        arrayList.add(AllansAutoRepairShop);
        arrayList.add(Nproject);
        arrayList.add(TransCycle);
        arrayList.add(WilbelBatterySupply);
        arrayList.add(JsRadiatorShop);
        arrayList.add(PerezBatteryShop);
        arrayList.add(JoyAutoSupply);
        arrayList.add(CanaveralAutoServiceCenter);
        arrayList.add(NorthFoilAndRetrofit);
        arrayList.add(NAutoElectricalSupply);
        arrayList.add(JCToriosAutoRepairWeldingShop);
        arrayList.add(AUTOWOKZSERVICELTD);
        arrayList.add(BigBrosTireOutlet);
        arrayList.add(LonAutoSupply);
        arrayList.add(JrRicCars);
        arrayList.add(TATAREYAUTOPAINTAUTOREPAIRAIRCONREPAIR);
        arrayList.add(InzaidwayCarTintandAccessories);
        arrayList.add(CastrolCarWorkshopCHECKPOINTSALESAUTOSOLUTIONSINC);
        arrayList.add(Km183EngineeringWorksEngineServices);
        arrayList.add(StarmanTireSupply);
        arrayList.add(BestoAutoSupply);
        arrayList.add(RACSAutomotiveUrdaneta);
        arrayList.add(UniversalAutoSupply);
        arrayList.add(HondaServiceCenter);
        arrayList.add(MaxmasterEnterprises);
        arrayList.add(CKCyclePartsGenMdse);
        arrayList.add(RCTireSupply);
        arrayList.add(SrjCarAutoPaintingShop);
        arrayList.add(ROADMASTERMUFFLERCENTER);
        arrayList.add(GSBatterySupply);
        arrayList.add(JandreiAutoParts);
        arrayList.add(FastLubesAutomotiveCenter);

        arrayList.add(SANCarAirconSpecialist);
        arrayList.add(TopstarTireSupply);
        arrayList.add(Station5GasandService);
        arrayList.add(AutoMagicTintandCarAirconService);
        arrayList.add(EvansCarwashDetailingCenter);
        arrayList.add(DenreedSapinoso);
        arrayList.add(RUGIESCARCENTERBARANGAYBACAG);
        arrayList.add(DarwinBoyAutoSupplyAndGeneralMerchandise);
        arrayList.add(SnRAutoShop);
        arrayList.add(RNRAutoRepairCenter);
        arrayList.add(CamillesCyclesandTrends);
        arrayList.add(JeshaiahsTrading);
        arrayList.add(RpsAutoSupply);
        arrayList.add(UncleGreyThaiparts);
        arrayList.add(JMAUTOZONECUSTOMPAINTSHOP);
        arrayList.add(RugiesCarCenter);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //assign variable
        gMap = googleMap;

        for(int i=0; i<arrayList.size();i++){

            gMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Marker"));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            gMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));


        }

        getCurrentLocation();
    }

    private void getCurrentLocation(){

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
            return;
        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(6000);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // Toast.makeText(MapsRepairShop.this, "Location result is: "+locationResult, Toast.LENGTH_SHORT).show();

                if(locationResult == null){
                    Toast.makeText(Repair_Services.this, "Location result is null: "+locationResult, Toast.LENGTH_SHORT).show();
                    return;
                }

                for(Location location:locationResult.getLocations()){
                    if(location!= null){
                        //Toast.makeText(MapsRepairShop.this, "Current location is: "+location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location !=null){

                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    LatLng latLng = new LatLng(lat, lng);
                    gMap.addMarker(new MarkerOptions().position(latLng).title("Hi you are here!").icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (Request_Code) {
            case Request_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //getCurrentLocation();
                }
        }
    }
}