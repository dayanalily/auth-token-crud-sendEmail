package com.daily.menu.springboot.auth;

public class JwtConfig {

	public static final String LLAVE_SECRETA = "dayana.sanchez.1234";
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\n" + 
			"MIIEowIBAAKCAQEAmbNfuA5Yc8Im6ddoNqag5rsNCroZ9HkbPb3n4XmABCUl4zrI\n" + 
			"LfniaxrVpGplHth8VOa3GzDnlSA476TivwcH5da5eJJtKFbDla01DtuVSdAcycgO\n" + 
			"JiCdfzZiCmsELp1T+QfYMq8odEcSWcVgV/4GyPPUCoH1ybw0naIpvJaln8s3Htsi\n" + 
			"1prf7lNMLwrpQ65//7TCX7IpvVpy5h5T7CxMp1bPpMzbcHrGm/f0S/vBCrDxJarf\n" + 
			"OOrhEavSsWvUqrZbNBSTCYjQ0lJnQQ0PgHGHhBTOhc7ELC81tUQaRQ7t8Vf0/XpL\n" + 
			"8xXvP0gPz0l889x3DwO8BFklCeYCbcY2asj6dwIDAQABAoIBACRGvdTHLgFEVcLj\n" + 
			"YGllspxCkbVybyXQX6mW5N/GuXxA+ExFV3HihJoa30nLAsrg9ZdkTNtSE5aS4F1P\n" + 
			"YWUM18RbhbL7fGAE+mydUOSM1xNw1xqY6R3+nK2lvEYRNA98epFQVKrBxJ6NF7uw\n" + 
			"Zijef+N2CRnhyo0uqox59bfZJoYsJtd+LKQlpr7bgImP+ruqLf1L4yJ5PucNXzkJ\n" + 
			"P6IlkaKKFAVvMsdeJ7tDr5jnYwxApMDiDf3zVHWhIC6KasMLxUdsWLhzEiFr5K4s\n" + 
			"XpGrMXajB7j0D3bDfnPp2N7NnQAakc1LN5URz/1b+AA37h+ik6YESHk6jypzeZ8B\n" + 
			"ffl9BiECgYEAxtRpt9fXCp2fPLca+7gNRsAnjmYm8+UcUoBoJkc7FFf6yZjIKFUN\n" + 
			"0vn2qQ6Xx6a9MjX5J+2gSURU03DA0nFSwoIHshhrLAQYbEhhlIrj/YOTpUW0G+Np\n" + 
			"HFVRcwqlEQOxd8Ycp/228XnWOdd+6H8XyZewLXxN4c6ZWFiKdmlzGu0CgYEAxeUS\n" + 
			"e7Sl3MmdoNQlnLg7+YFTEj1C5VbQl5X26XeTFeIyzdmm9Imilu176tdTOeBZagqG\n" + 
			"gVcNpeMYAoCCVu//CA8rIVeqNl+0BUO5gK9Zt8hsiJSUl9HT2U1GL6h3ClBdClwG\n" + 
			"PYpd6h+LkMjbF2jGNktNwYwqGxjUxdisimYtKnMCgYA3EICuv4H4mKqPAA7jA5f5\n" + 
			"riHGWBgVl6/+UT2GzFdaTPtRdZSJRvTrJvre2wHB8zWWgEgefLrVT61GWYWtQmEv\n" + 
			"KR/EfOrBZ+jzpCASKVz2ysfLD8DPsG1dwWsrNxl72s/9Ycpx49wUiC+CGWI2NWx7\n" + 
			"Eh1im3hePv9M7kh6fr24CQKBgDCrc+5EsX4pf8DsLAvtK6LjcYDM3ET3IvoFAeLN\n" + 
			"a7k4FnKWEv+VUg14fr91EZzsVlFEIWxx8oNX5LHI1UV4ZOqggGUE4HHa+7VHwLmr\n" + 
			"Zo9SlY7Jq/niuw14eciC0DQTK66+jzJXc11/QJ3tKCULuaPkxUzSohBj/QBjl5u6\n" + 
			"IWWNAoGBAL88mbyKquFY8osi1LenL0djiftu+6wCRjIF5bJ+Y+dvVWqelqw1Hic8\n" + 
			"FklKE3V5cGDItFWmLF59EulVRibW0e7YLEd9k4IYSL1GKz906jV7WaxBwiZtWNAr\n" + 
			"44E9pTDoDeFwnoKFAv+/pP6fbYLOqomGoTPR9GvYlE4waJAgD7Ux\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmbNfuA5Yc8Im6ddoNqag\n" + 
			"5rsNCroZ9HkbPb3n4XmABCUl4zrILfniaxrVpGplHth8VOa3GzDnlSA476TivwcH\n" + 
			"5da5eJJtKFbDla01DtuVSdAcycgOJiCdfzZiCmsELp1T+QfYMq8odEcSWcVgV/4G\n" + 
			"yPPUCoH1ybw0naIpvJaln8s3Htsi1prf7lNMLwrpQ65//7TCX7IpvVpy5h5T7CxM\n" + 
			"p1bPpMzbcHrGm/f0S/vBCrDxJarfOOrhEavSsWvUqrZbNBSTCYjQ0lJnQQ0PgHGH\n" + 
			"hBTOhc7ELC81tUQaRQ7t8Vf0/XpL8xXvP0gPz0l889x3DwO8BFklCeYCbcY2asj6\n" + 
			"dwIDAQAB\n" + 
			"-----END PUBLIC KEY-----"; 
}
