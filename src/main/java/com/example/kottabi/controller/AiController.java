
		package com.example.kottabi.controller;

import com.example.kottabi.DTO.AI_DTO.AiRapportRequestDTO;
import com.example.kottabi.DTO.AI_DTO.AiRapportResponceDTO;
import com.example.kottabi.services.AIRapportGenerator;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/rapport")
public class AiController {

	private final AIRapportGenerator aiRapportGenerator;
	private final ChatClient chatClient;

	public AiController(
			AIRapportGenerator aiRapportGenerator,
			ChatClient.Builder chatClientBuilder
	) {
		this.aiRapportGenerator = aiRapportGenerator;
		this.chatClient = chatClientBuilder.build();
	}

	@GetMapping("/ask")
	public String ask(@RequestParam String ask) {
		return chatClient
				.prompt()
				.user(ask)
				.call()
				.content();
	}

	@PostMapping("/{id}")
	public AiRapportResponceDTO sendRapport(@PathVariable long id) {

		AiRapportRequestDTO student =
				aiRapportGenerator.createEleveRapportAi(id);

		return chatClient
				.prompt()
				.system(
						"أنت مساعد تعليمي في تطبيق كتّابي. " +
								"مهمتك إعداد تقرير مختصر وواضح عن طالب في حفظ القرآن الكريم. " +
								"حلّل البيانات المقدمة فقط، ولا تخترع أي معلومات. " +
								"اكتب باللغة العربية الفصحى فقط، بأسلوب تربوي واضح ومحترم."
				)
				.user(
						"بيانات الطالب:\n\n" +

								"الاسم: " +
								student.getPrenom() + " " +
								student.getNom() + "\n" +

								"تاريخ الميلاد: " +
								student.getDateNaissance() + "\n\n" +

								"الحضور:\n" +
								student.getPresences() + "\n\n" +

								"تقدم حفظ القرآن الكريم:\n" +
								student.getProgressions() + "\n\n" +

								"المشاركة في المسابقات القرآنية:\n" +
								student.getParticipations() + "\n\n" +

								"المطلوب:\n" +
								"أنشئ تقريراً مختصراً ومنظماً باللغة العربية الفصحى فقط.\n\n" +

								"يجب أن يتضمن:\n" +
								"1. معلومات الطالب.\n" +
								"2. نسبة الحضور والغياب بناءً على سجل الحضور.\n" +
								"3. نسبة التقدم في حفظ القرآن الكريم بناءً على بيانات التقدم.\n" +
								"4. ملخصاً قصيراً عن المشاركة في المسابقات القرآنية.\n" +
								"5. تقييماً عاماً مختصراً لمستوى الطالب.\n" +
								"6. توصية تربوية قصيرة للمعلم.\n\n" +

								"لا تطل في الشرح. " +
								"استخدم عناوين واضحة ونقاطاً مختصرة. " +
								"لا تضف أي معلومة غير موجودة في البيانات."
				)
				.call()
				.entity(AiRapportResponceDTO.class);
	}
}

