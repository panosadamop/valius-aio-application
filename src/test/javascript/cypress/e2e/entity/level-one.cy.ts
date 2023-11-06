import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('LevelOne e2e test', () => {
  const levelOnePageUrl = '/level-one';
  const levelOnePageUrlPattern = new RegExp('/level-one(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const levelOneSample = {
    identifier: 'challenge Tuna content',
    companyName: 'Dam Pizza invoice',
    productsServices: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    mission: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    vision: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    companyValues: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    targetAudienceDescription: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    potentialCustomersGroups: 'Bhutanese',
    strengths: 'foreground Plastic bandwidth',
    weaknesses: 'calculate eyeballs',
    opportunities: 'Crossing feed SMS',
    threats: 'mobile',
  };

  let levelOne;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/level-ones+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/level-ones').as('postEntityRequest');
    cy.intercept('DELETE', '/api/level-ones/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (levelOne) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/level-ones/${levelOne.id}`,
      }).then(() => {
        levelOne = undefined;
      });
    }
  });

  it('LevelOnes menu should load LevelOnes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('level-one');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('LevelOne').should('exist');
    cy.url().should('match', levelOnePageUrlPattern);
  });

  describe('LevelOne page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(levelOnePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create LevelOne page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/level-one/new$'));
        cy.getEntityCreateUpdateHeading('LevelOne');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelOnePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/level-ones',
          body: levelOneSample,
        }).then(({ body }) => {
          levelOne = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/level-ones+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/level-ones?page=0&size=20>; rel="last",<http://localhost/api/level-ones?page=0&size=20>; rel="first"',
              },
              body: [levelOne],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(levelOnePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details LevelOne page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('levelOne');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelOnePageUrlPattern);
      });

      it('edit button click should load edit LevelOne page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelOne');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelOnePageUrlPattern);
      });

      it('edit button click should load edit LevelOne page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelOne');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelOnePageUrlPattern);
      });

      it('last delete button click should delete instance of LevelOne', () => {
        cy.intercept('GET', '/api/level-ones/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('levelOne').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelOnePageUrlPattern);

        levelOne = undefined;
      });
    });
  });

  describe('new LevelOne page', () => {
    beforeEach(() => {
      cy.visit(`${levelOnePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('LevelOne');
    });

    it('should create an instance of LevelOne', () => {
      cy.get(`[data-cy="identifier"]`).type('transmitting Vermont').should('have.value', 'transmitting Vermont');

      cy.get(`[data-cy="companyName"]`).type('Legacy Egypt').should('have.value', 'Legacy Egypt');

      cy.setFieldImageAsBytesOfEntity('companyLogo', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="brandName"]`).type('Forward streamline transmit').should('have.value', 'Forward streamline transmit');

      cy.setFieldImageAsBytesOfEntity('productLogo', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="industry"]`).type('Specialist Bedfordshire').should('have.value', 'Specialist Bedfordshire');

      cy.get(`[data-cy="organizationType"]`).type('online').should('have.value', 'online');

      cy.get(`[data-cy="productsServices"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="territory"]`).type('object-oriented').should('have.value', 'object-oriented');

      cy.get(`[data-cy="noEmployees"]`).type('Ball').should('have.value', 'Ball');

      cy.get(`[data-cy="revenues"]`).type('neutral Dynamic Market').should('have.value', 'neutral Dynamic Market');

      cy.get(`[data-cy="mission"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="vision"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="companyValues"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="strategicFocus"]`).type('Fresh evolve back').should('have.value', 'Fresh evolve back');

      cy.get(`[data-cy="marketingBudget"]`).type('matrix Walk').should('have.value', 'matrix Walk');

      cy.get(`[data-cy="productDescription"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="maturityPhase"]`).type('hacking Fish').should('have.value', 'hacking Fish');

      cy.get(`[data-cy="competitivePosition"]`).type('action-items Gorgeous').should('have.value', 'action-items Gorgeous');

      cy.get(`[data-cy="targetAudienceDescription"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="potentialCustomersGroups"]`).type('Corner').should('have.value', 'Corner');

      cy.get(`[data-cy="strengths"]`).type('navigating Interactions').should('have.value', 'navigating Interactions');

      cy.get(`[data-cy="weaknesses"]`).type('Home').should('have.value', 'Home');

      cy.get(`[data-cy="opportunities"]`).type('purple Tonga').should('have.value', 'purple Tonga');

      cy.get(`[data-cy="threats"]`).type('override tertiary override').should('have.value', 'override tertiary override');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        levelOne = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', levelOnePageUrlPattern);
    });
  });
});
