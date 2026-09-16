package com.example.kottabi.controller;

import com.example.kottabi.DTO.AI_DTO.AiRapportRequestDTO;
import com.example.kottabi.services.AIRapportGenerator;
import com.example.kottabi.services.ServiceImp.PdfRapportService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/rapport")
public class AiController {

	private final PdfRapportService pdfRapportService;
	private final AIRapportGenerator aiRapportGenerator;
	private final ChatClient chatClient;

	public AiController(
		PdfRapportService pdfRapportService,
		AIRapportGenerator aiRapportGenerator,
		ChatClient.Builder chatClientBuilder
	) {
		this.pdfRapportService = pdfRapportService;
		this.aiRapportGenerator = aiRapportGenerator;
		this.chatClient = chatClientBuilder.build();
	}

	@PostMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public String sendRapport(@PathVariable long id) {
		AiRapportRequestDTO student = aiRapportGenerator.createEleveRapportAi(id);

		String rapport = chatClient
			.prompt()
			.system(
				"""
                        أنت مساعد تربوي في تطبيق "كتّابي".

                        مهمتك إعداد تقرير تربوي مختصر واحترافي عن طالب في حفظ القرآن الكريم.

                        اعتمد حصراً على البيانات المقدمة.
                        لا تخترع أي معلومة ولا تستنتج معلومات غير موجودة في البيانات.
                        إذا كانت بعض البيانات غير متوفرة، لا تذكرها.

                        اكتب باللغة العربية الفصحى فقط.
                        استخدم أسلوباً تربوياً محترماً وواضحاً ومناسباً لتقرير مدرسي.
                        لا تستخدم عبارات مبالغ فيها.
                        لا تضع نسباً أو أرقاماً إلا إذا كانت محسوبة فعلياً من البيانات.
                        """
			)
			.user(
				"""
                        بيانات الطالب:

                        الاسم:
                        %s %s

                        تاريخ الميلاد:
                        %s

                        سجل الحضور:
                        %s

                        تقدم حفظ القرآن الكريم:
                        %s

                        المشاركة في المسابقات القرآنية:
                        %s


                        المطلوب:

                        أنشئ تقريراً تربوياً مختصراً ومنظماً.

                        يجب أن يحتوي التقرير على الأقسام التالية:

                        1. معلومات الطالب
                        اذكر اسم الطالب وتاريخ الميلاد فقط.

                        2. الحضور والمواظبة
                        لخّص وضع الحضور والغياب اعتماداً على سجل الحضور لآخر شهر.
                        إذا كانت البيانات تسمح بحساب النسبة، اذكرها.
                        ثم قدم جملة قصيرة عن مستوى المواظبة.

                        3. مستوى حفظ القرآن الكريم
                        لخّص ما تم حفظه اعتماداً على بيانات التقدم فقط.
                        اذكر السور والآيات أو الأجزاء الموجودة في البيانات.
                        صف مستوى التقدم بشكل مختصر دون اختراع تقييم رقمي.

                        4. المشاركة في المسابقات
                        لخّص مشاركات الطالب في المسابقات القرآنية، مع ذكر النتائج
                        أو الترتيب أو الملاحظات إذا كانت موجودة.

                        5. التقييم العام
                        قدم تقييماً تربوياً قصيراً مبنياً فقط على البيانات المتوفرة.

                        قواعد مهمة:
                        - التقرير مختصر ومهني.
                        - لا تكرر نفس المعلومات.
                        - لا تستخدم جداول.
                        - لا تستخدم Markdown معقداً.
                        - استخدم عناوين واضحة.
                        - لا تضف مقدمة طويلة أو خاتمة عامة.
                        - لا تخترع نسباً أو معلومات غير موجودة.
                        """.formatted(
						student.getPrenom(),
						student.getNom(),
						student.getDateNaissance(),
						student.getPresences(),
						student.getProgressions(),
						student.getParticipations()
					)
			)
			.call()
			.content();

		pdfRapportService.generatePdf(rapport, id);

		return "PDF report generated successfully for student " + id;
	}
}
