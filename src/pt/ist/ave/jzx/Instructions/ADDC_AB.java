package pt.ist.ave.jzx.Instructions;

public class ADDC_AB extends Instruction {

	public ADDC_AB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//add_a(m_a8);
		_cpu.adc_a(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		//m_tstates += 4;
		return 4;
	}

}
