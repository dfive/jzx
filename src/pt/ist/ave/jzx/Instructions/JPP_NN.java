package pt.ist.ave.jzx.Instructions;

public class JPP_NN extends Instruction {

	public JPP_NN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		if (!m_signF) {
//		m_pc16 = m_memory.read16(m_pc16);
//	} else {
//		m_pc16 = incinc16(m_pc16);
//	}
	if (!_cpu.getM_signF()) {
		_cpu.setM_pc16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
	} else {
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
	}
	}

	@Override
	public int incTstates() {
//		m_tstates += 10;
		return 10;
	}

}
